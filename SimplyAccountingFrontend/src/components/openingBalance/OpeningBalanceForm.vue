<template>
  <div class="opening-balance-form">
    <v-card v-if="isFormVisible">

      <v-card-title class="primary">
        {{ $_.capitalize($t('openingBalance.openingBalance')) }}
      </v-card-title>

      <v-card-text class="mt-4 pb-1">
        <validation-observer ref="observer" v-slot="{handleSubmit}">
          <form id="opening-balance-form" @submit.prevent="handleSubmit(createEditOpeningBalance)">

            <validation-provider
                :name="$t('cooperative.cooperative')"
                :rules="{required: true}"
                v-slot="validationContext">

              <autocomplete-input
                  v-model="openingBalance.cooperativeId"
                  tag-name="cooperative"
                  :items="cooperatives"
                  item-text-property="name"
                  item-value-property="id"
                  :label="$_.capitalize($t('cooperative.cooperative'))"
                  :hint="$t('rate.messages.chooseCooperative')"
                  :disabled="isCooperativeSelectDisabled"
                  :errors="validationContext.errors"/>
            </validation-provider>

            <validation-provider
                :name="$t('common.year')"
                :rules="{required: true}"
                v-slot="validationContext">

              <year-select
                  v-model.number="openingBalance.year"
                  :errors="validationContext.errors"
                  :label="$_.capitalize($t('common.year'))"
                  :disabled="isOpeningBalanceFound"/>
            </validation-provider>

            <validation-provider
                :name="$t('openingBalance.balance')"
                :rules="{required: true}"
                v-slot="validationContext">

              <number-input
                  v-model.number="openingBalance.balance"
                  tag-name="balance"
                  :step="0.01"
                  :errors="validationContext.errors"
                  :label="$_.capitalize($t('openingBalance.balance'))"
                  :number-formatter="currencyFormatter"/>
            </validation-provider>

          </form>
        </validation-observer>
      </v-card-text>

      <v-card-actions class="pb-4">
        <v-spacer/>

        <default-button
            type="submit"
            form="opening-balance-form"
            :text="createOrEdit"/>

        <default-button
            @click="cancelForm"
            :text="$_.capitalize($t('common.cancel'))"/>
      </v-card-actions>
    </v-card>

    <div v-else-if="id != null">
      <h2>{{ $t('openingBalance.messages.notFound') }}</h2>
    </div>

    <div v-else>
      <h2>{{ $t('openingBalance.messages.accountError') }}</h2>
    </div>

  </div>
</template>

<script>
import OpeningBalanceService from "@/core/service/OpeningBalanceService";
import {fixedCurrency} from "@/core/service/FormatterService";
import CooperativeService from "@/core/service/CooperativeService";
import AccountService from "@/core/service/AccountService";
import AutocompleteInput from "@/components/common/form/AutocompleteInput";
import NumberInput from "@/components/common/form/NumberInput";
import DefaultButton from "@/components/common/buttons/DefaultButton";
import YearSelect from "@/components/common/form/YearSelect";


export default {
  name: "OpeningBalanceForm",
  components: {YearSelect, DefaultButton, NumberInput, AutocompleteInput},
  props: ['id', 'accountId'],
  data() {
    return {
      openingBalance: {
        id: null,
        year: null,
        balance: null,
        accountId: null,
        cooperativeId: null
      },
      cooperatives: [],
      cooperativeId: null
    }
  },
  methods: {
    cancelForm() {
      this.$emit('cancel');
    },
    createEditOpeningBalance() {
      this.$store.commit('switchOnLoading');
      this.$loadingPromises([this.createEditOpeningBalanceRequest()]);
    },
    createEditOpeningBalanceRequest() {

      if (this.openingBalance.id == null) {
        return OpeningBalanceService.createOpeningBalance(this.openingBalance)
            .then(response => {
              this.$successToast(this.$t('openingBalance.messages.created'));
              this.$emit('updateOpeningBalances', response.data);
            })
            .catch(error => {
              this.$errorToast(error);
            });
      } else {
        return OpeningBalanceService.editOpeningBalance(this.openingBalance)
            .then(response => {
              this.$successToast(this.$t('openingBalance.messages.edited'));
              this.$emit('updateOpeningBalances', response.data);
            })
            .catch(error => {
              this.$errorToast(error);
            });
      }
    },
    loadOpeningBalance() {
      this.$loadingPromises([this.loadOpeningBalanceRequest()]);
    },
    loadOpeningBalanceRequest() {
      return OpeningBalanceService.getOpeningBalance(this.id)
          .then(async response => {
            this.openingBalance = response.data;
            await this.loadAccountRequest();
          })
          .catch(error => {
            this.openingBalance.id = null;
            this.$errorToast(error);
          });
    },
    loadAllCooperative() {
      this.$loadingPromises([this.loadAllCooperativeRequest()]);
    },
    loadAllCooperativeRequest() {
      return CooperativeService.getAllCooperatives()
          .then(response => {
            this.cooperatives = response.data;
          })
          .catch(error => {
            this.cooperatives = [];
            this.$errorToast(error);
          });
    },
    loadAccount() {
      this.$loadingPromises([this.loadAccountRequest()]);
    },
    loadAccountRequest() {
      return AccountService.getAccount(this.openingBalance.accountId)
          .then(response => {
            this.openingBalance.accountId = response.data.id;
            this.cooperativeId = response.data.cooperativeId;
            if (response.data.accountType.id === 1) {
              this.openingBalance.cooperativeId = response.data.cooperativeId;
            }
          })
          .catch(error => {
            this.openingBalance.accountId = null;
            this.$errorToast(error);
          });
    },
    currencyFormatter(value) {
      return fixedCurrency(value);
    }
  },
  computed: {
    isFormVisible() {
      return this.isOpeningBalanceFound || this.openingBalance.accountId != null;
    },
    createOrEdit() {
      return this.id != null ? this.$_.capitalize(this.$t('common.edit')) : this.$_.capitalize(this.$t('common.create'));
    },
    isOpeningBalanceFound() {
      return this.openingBalance.id != null;
    },
    isCooperativeSelectDisabled() {
      return this.cooperativeId != null || this.openingBalance.id != null;
    }
  },
  mounted() {
    const promises = [this.loadAllCooperativeRequest()];

    if (this.id != null) {
      promises.push(this.loadOpeningBalanceRequest());
    } else {
      this.openingBalance.accountId = this.accountId;
      this.openingBalance.year = new Date().getFullYear();
      promises.push(this.loadAccountRequest());
    }

    this.$loadingPromises(promises);
  },
  watch: {
    id() {
      if (this.id != null) {
        this.loadOpeningBalance();
      } else {
        this.openingBalance.id = null;
        this.openingBalance.accountId = this.accountId;
        this.loadAccount();
      }
    },
    accountId() {
      if (this.id == null) {
        this.openingBalance.accountId = this.accountId;
        this.loadAccount();
      }
    }
  }
}
</script>

<style scoped>

</style>