<template>
  <div class="cooperative-view mb-8">
    <v-data-table v-if="isCooperativeFound"
                  :headers="tableHeaders"
                  :items="cooperative.buildings"
                  disable-pagination
                  multi-sort
                  hide-default-footer
                  :no-data-text="$t('cooperative.messages.noBuilding')"
                  class="elevation-1"
                  @click:row="goToBuilding">

      <template #top>
        <v-card-title class="position-relative">
          <div class="text-h4 mr-8">
            {{ cooperative.name }}
            <edit-button @click="editCooperative"/>
          </div>

          <v-menu
              left
              bottom>
            <template #activator="{ on, attrs }">
              <v-btn
                  class="menu-button mr-2 mt-4 mr-sm-4"
                  v-bind="attrs"
                  v-on="on"
                  :icon="isSmall"
                  :outlined="!isSmall">
                <v-icon v-if="isSmall">
                  mdi-dots-vertical
                </v-icon>
                <template v-if="!isSmall">
                  {{ $_.capitalize($t('common.options')) }}
                </template>
              </v-btn>
            </template>
            <v-list>
              <v-list-item
                  v-for="(option, index) in menuOptions"
                  :key="index"
                  @click="option.onClickFunction">
                <v-list-item-title>
                  {{ option.text }}
                </v-list-item-title>
              </v-list-item>
            </v-list>
          </v-menu>
        </v-card-title>

        <v-card-subtitle class="py-0">
          <div class="text-h6">{{ cooperative.address }}</div>
        </v-card-subtitle>

        <v-card-subtitle class="pt-0">
          <div class="text-h6">{{ cooperative.nip }}</div>
        </v-card-subtitle>
        <v-divider/>
      </template>

      <template #footer>

        <v-divider/>

        <div class="text-center">
          <add-button
              class="my-2"
              @click="createBuilding"/>
        </div>

      </template>

    </v-data-table>

    <div v-else>
      {{ $t("cooperative.messages.notFound") }}
    </div>

    <v-dialog
        v-model="isAdvanceFormVisible"
        max-width="400px">
      <calculate-advance-form
          :cooperative-id="cooperative.id"
          @cancel="hideCalculateAdvanceForm"
          @calculated="hideCalculateAdvanceForm"/>
    </v-dialog>
    <v-dialog
            v-model="isClosingBalanceFormVisible"
            max-width="400px">
      <calculate-closing-balance-form
              :cooperative-id="cooperative.id"
              @cancel="hideCalculateClosingBalancesForm"
              @calculated="hideCalculateClosingBalancesForm"/>
    </v-dialog>

  </div>
</template>

<script>
import CooperativeService from "@/core/service/CooperativeService";
import CalculateAdvanceForm from "@/components/advance/CalculateAdvanceForm";
import EditButton from "@/components/common/buttons/EditButton";
import AddButton from "@/components/common/buttons/AddButton";
import CalculateClosingBalanceForm from "../openingBalance/CalculateClosingBalanceForm";

export default {
  name: "CooperativeView",
  components: {CalculateClosingBalanceForm, AddButton, EditButton, CalculateAdvanceForm},
  props: ['id'],
  data() {
    return {
      cooperative: {
        id: null,
        name: String,
        accountId: null
      },
      isAdvanceFormVisible: false,
      isClosingBalanceFormVisible: false,
    }
  },
  mounted() {
    this.loadCooperative();
  },
  computed: {
    isCooperativeFound() {
      return this.cooperative.id != null;
    },
    isSmall() {
      return this.$vssWidth <= 600;
    },
    tableHeaders() {
      return [
        {
          text: this.$_.capitalize(this.$t('common.street')),
          value: 'street'
        },
        {
          text: this.$_.capitalize(this.$t('common.houseNumber')),
          value: 'houseNumber'
        },
        {
          text: this.$_.capitalize(this.$t('common.postalCode')),
          value: 'postalCode'
        },
        {
          text: this.$_.capitalize(this.$t('common.city')),
          value: 'city'
        },
      ];
    },
    menuOptions() {
      return [
        {
          text: this.$_.capitalize(this.$t('openingBalance.openingBalances')),
          onClickFunction: this.goToOpeningBalances
        },
        {
          text: this.$_.capitalize(this.$t('invoice.invoices')),
          onClickFunction: this.goToInvoices
        },
        {
          text: this.$_.capitalize(this.$t('charge.chargesPlan')),
          onClickFunction: this.goToChargesPlan
        },
        {
          text: this.$_.capitalize(this.$t('payment.bank')),
          onClickFunction: this.goToPayments
        },
        {
          text: this.$_.capitalize(this.$t('common.calculateAdvance')),
          onClickFunction: this.openCalculateAdvanceForm
        },
        {
          text: this.$_.capitalize(this.$t('common.calculateClosingBalance')),
          onClickFunction: this.openCalculateClosingBalancesForm
        },
      ];
    }
  },
  methods: {
    openCalculateAdvanceForm() {
      this.isAdvanceFormVisible = true;
    },
    hideCalculateAdvanceForm() {
      this.isAdvanceFormVisible = false;
    },
    openCalculateClosingBalancesForm(){
      this.isClosingBalanceFormVisible = true;
    },
    hideCalculateClosingBalancesForm() {
      this.isClosingBalanceFormVisible = false;
    },
    loadCooperative() {
      this.$loadingPromises([this.loadCooperativeRequest()]);
    },
    goToOpeningBalances() {
      this.$router.push({
        name: 'openingBalancesList',
        params: {accountId: this.cooperative.accountId}
      });
    },
    goToInvoices() {
      this.$router.push({
        name: 'invoiceListCooperative',
        params: {cooperativeId: this.cooperative.id}
      });
    },
    goToChargesPlan() {
      this.$router.push({
        name: 'chargesPlan',
        params: {cooperativeId: this.cooperative.id}
      });
    },
    goToPayments() {
      this.$router.push({
        name: 'cooperativePayments',
        params: {cooperativeId: this.cooperative.id}
      });
    },
    loadCooperativeRequest() {
      return CooperativeService.getCooperativesBuildings(this.id)
          .then(response => {
            this.cooperative = response.data;
          })
          .catch(error => {
            this.cooperative.id = null;
            this.$errorToast(error);
          });
    },
    createBuilding() {
      this.$router.push({name: 'buildingCreate', params: {cooperativeId: this.id}});
    },
    goToBuilding(building) {
      this.$router.push({name: 'building', params: {id: building.id}});
    },
    editCooperative() {
      this.$router.push({name: 'cooperativeEdit', params: {id: this.id}});
    }
  },
  watch: {
    id() {
      this.loadCooperative();
    }
  }
}
</script>

<style scoped>

/deep/ tbody > tr {
  cursor: pointer !important;
}

</style>
