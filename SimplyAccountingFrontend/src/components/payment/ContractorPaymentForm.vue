<template>
  <v-card class="contractor-payment-form">

    <v-card-title class="primary">
      {{ $_.capitalize($t('payment.bank')) + ' - ' + $t('payment.contractorPayment') }}
    </v-card-title>

    <v-card-text class="mt-4 pb-1">
      <div v-if="isFormVisible">
        <validation-observer ref="observer" v-slot="{handleSubmit}">
          <form id="contractor-payment-form" @submit.prevent="handleSubmit(createEditContractorPayment)">

            <validation-provider
                :name="$t('payment.value')"
                :rules="{required: true}"
                v-slot="validationContext">

              <number-input
                  v-model.number="contractorPayment.value"
                  tag-name="payment-value"
                  :step="0.01"
                  :errors="validationContext.errors"
                  :label="$_.capitalize($t('payment.value'))"
                  :number-formatter="currencyFormatter"/>
            </validation-provider>

            <validation-provider
                :name="$t('common.realizationDate')"
                :rules="{required: true}"
                v-slot="validationContext">

              <date-input
                  v-model="contractorPayment.realizationDate"
                  tag-name="realization-date"
                  :errors="validationContext.errors"
                  :label="$_.capitalize($t('common.realizationDate'))"/>
            </validation-provider>

            <validation-provider
                :name="$t('contractor.contractor')"
                :rules="{required: true}"
                v-slot="validationContext">

              <autocomplete-input
                  v-model="contractorPayment.contractorId"
                  tag-name="contractor"
                  :items="contractors"
                  item-text-property="name"
                  item-value-property="id"
                  :label="$_.capitalize($t('contractor.contractor'))"
                  :hint="$t('payment.messages.chooseContractor')"
                  :disabled="id != null"
                  :errors="validationContext.errors"/>
            </validation-provider>

          </form>
        </validation-observer>
      </div>

      <div v-else>
        <h2>{{ $t('payment.messages.notFound') }}</h2>
      </div>

    </v-card-text>

    <v-card-actions class="mt-2 mr-2 pb-4">
      <v-spacer/>

      <default-button
          v-if="isFormVisible"
          form="contractor-payment-form"
          type="submit"
          :text="createOrEdit"/>

      <default-button
          @click="cancelForm"
          :text="$_.capitalize($t('common.cancel'))"/>
    </v-card-actions>

  </v-card>
</template>

<script>
import {fixedCurrency} from "@/core/service/FormatterService";
import ContractorPaymentService from "@/core/service/ContractorPaymentService";
import ContractorService from "@/core/service/ContractorService";
import NumberInput from "@/components/common/form/NumberInput";
import DateInput from "@/components/common/form/DateInput";
import AutocompleteInput from "@/components/common/form/AutocompleteInput";
import DefaultButton from "@/components/common/buttons/DefaultButton";

export default {
  name: "ContractorPaymentForm",
  components: {DefaultButton, AutocompleteInput, DateInput, NumberInput},
  props: ['id', 'cooperativeId'],
  data() {
    return {
      contractorPayment: {
        id: null,
        contractorId: null,
        cooperativeId: null,
        realizationDate: null,
        value: null
      },
      contractors: []
    }
  },
  computed: {
    createOrEdit() {
      return this.id != null ? this.$_.capitalize(this.$t('common.edit')) : this.$_.capitalize(this.$t('common.create'));
    },
    isContractorPaymentFound() {
      return this.contractorPayment.id != null;
    },
    isFormVisible() {
      return this.isContractorPaymentFound || this.id == null;
    }
  },
  methods: {
    currencyFormatter(value) {
      return fixedCurrency(value);
    },
    loadContractorPayment() {
      this.$loadingPromises([this.loadContractorPaymentRequest()]);
    },
    loadContractorPaymentRequest() {
      return ContractorPaymentService.getContractorPayment(this.id)
          .then(async response => {
            this.contractorPayment = response.data;
            await this.loadContractorRequest();
          })
          .catch(error => {
            this.contractorPayment.id = null;
            this.$errorToast(error);
          });
    },
    loadAllContractors() {
      this.$loadingPromises([this.loadAllContractorsRequest()]);
    },
    loadAllContractorsRequest() {
      return ContractorService.getAllContractors()
          .then(response => {
            this.contractors = response.data;
          })
          .catch(error => {
            this.contractors = [];
            this.$errorToast(error);
          });
    },
    createEditContractorPayment() {
      this.$loadingPromises([this.createEditContractorPaymentRequest()]);
    },
    createEditContractorPaymentRequest() {
      if (this.id == null) {
        return ContractorPaymentService.createContractorPayment(this.contractorPayment)
            .then(() => {
              this.$successToast(this.$t('payment.messages.created'));
              this.cancelForm();
            })
            .catch(error => {
              this.$errorToast(error);
            });
      } else {
        return ContractorPaymentService.editContractorPayment(this.contractorPayment)
            .then(() => {
              this.$successToast(this.$t('payment.messages.edited'));
              this.cancelForm();
            })
            .catch(error => {
              this.$errorToast(error);
            });
      }
    },
    loadContractor() {
      this.$loadingPromises([this.loadContractorRequest()]);
    },
    loadContractorRequest() {
      return ContractorService.getContractor(this.contractorPayment.contractorId)
          .then(response => {
            this.contractors = [response.data];
          })
          .catch(error => {
            this.contractors = [];
            this.$errorToast(error);
          });
    },
    cancelForm() {
      this.$router.push({
        name: 'cooperativePayments',
        params: {cooperativeId: this.contractorPayment.cooperativeId}
      });
    }
  },
  mounted() {
    const promises = [];

    if (this.id != null) {
      promises.push(this.loadContractorPaymentRequest());
    } else {
      promises.push(this.loadAllContractorsRequest());
      this.contractorPayment.cooperativeId = this.cooperativeId;
    }

    this.$loadingPromises(promises);
  },
  watch: {
    id() {
      if (this.cooperativeId == null || this.id != null) {
        this.loadContractorPayment();
      }
    },
    cooperativeId() {
      if (this.id == null) {
        this.loadAllContractors();
        this.contractorPayment.cooperativeId = this.cooperativeId;
      }
    }
  }
}
</script>

<style scoped>

</style>