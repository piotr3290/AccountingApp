<template>
  <div class="accounts-plan">
    <v-card class="mb-8">
      <v-card-title class="text-h4 justify-center">
        {{ $_.capitalize($t('account.plan')) }}
      </v-card-title>
      <v-divider/>
      <v-row class="mx-0 mt-0">

        <v-col md lg xl class="col-12 col-sm-6">

          <div class="text-h5">{{ $_.capitalize($t('advance.advances')) }}</div>

          <v-divider/>

          <charge-type-list :charge-types="chargeTypeList"/>
        </v-col>

        <v-divider class="my-2" vertical/>

        <v-col md lg xl class="col-12 col-sm-6">

          <div class="text-h5">{{ $_.capitalize($t('landlord.landlord')) }}</div>

          <v-divider/>

          <v-list class="py-0">
            <v-list-item>
              <v-list-item-title>
                {{ landlordAccountType.number + ' - ' + landlordAccountType.name }}
              </v-list-item-title>

              <edit-button
                  class="ml-2"
                  @click="openLandlordAccountForm"/>

            </v-list-item>
          </v-list>


        </v-col>

        <v-divider class="my-2" vertical/>

        <v-col md lg xl class="col-12 col-sm-6">

          <div class="text-h5">{{ $_.capitalize($t('cooperative.cooperative')) }}</div>

          <v-divider/>

          <v-list class="py-0">
            <v-list-item>
              <v-list-item-title>
                {{ cooperativeAccountType.number + ' - ' + cooperativeAccountType.name }}
              </v-list-item-title>

              <edit-button
                  class="ml-2"
                  @click="openCooperativeAccountForm"/>

            </v-list-item>
          </v-list>

        </v-col>

        <v-divider class="my-2" vertical/>

        <v-col md lg xl class="col-12 col-sm-6">

          <div class="text-h5">{{ $_.capitalize($t('contractor.contractor')) }}</div>

          <v-divider/>

          <v-list class="py-0">
            <v-list-item>
              <v-list-item-title>
                {{ contractorAccountType.number + ' - ' + contractorAccountType.name }}
              </v-list-item-title>

              <edit-button
                  class="ml-2"
                  @click="openContractorAccountForm"/>

            </v-list-item>
          </v-list>

        </v-col>

        <v-divider class="my-2" vertical/>

        <v-col md lg xl class="col-12 col-sm-12">

          <div class="text-h5">{{ $_.capitalize($t('invoice.invoice')) }}</div>

          <v-divider/>

          <invoice-type-list :invoice-types="invoiceTypeList"/>
        </v-col>

      </v-row>
    </v-card>

    <v-dialog
        v-model="isLandlordAccountFormVisible"
        max-width="400px">

      <account-type-form :id="landlordAccountType.id"
                         @edited="updateLandlordAccount($event)"
                         @cancel="hideLandlordAccountForm"/>
    </v-dialog>

    <v-dialog
        v-model="isCooperativeAccountFormVisible"
        max-width="400px">

      <account-type-form :id="cooperativeAccountType.id"
                         @edited="updateCooperativeAccount($event)"
                         @cancel="hideCooperativeAccountForm"/>
    </v-dialog>

    <v-dialog
        v-model="isContractorAccountFormVisible"
        max-width="400px">

      <account-type-form :id="contractorAccountType.id"
                         @edited="updateContractorAccount($event)"
                         @cancel="hideContractorAccountForm"/>
    </v-dialog>

  </div>
</template>

<script>
import AccountTypeService from "@/core/service/AccountTypeService";
import AccountTypeForm from "@/components/account/AccountTypeForm";
import InvoiceTypeList from "@/components/invoice/InvoiceTypeList";
import InvoiceTypeService from "@/core/service/InvoiceTypeService";
import ChargeTypeService from "@/core/service/ChargeTypeService";
import ChargeTypeList from "@/components/charge/ChargeTypeList";
import EditButton from "@/components/common/buttons/EditButton";

export default {
  name: "AccountsPlan",
  components: {EditButton, ChargeTypeList, InvoiceTypeList, AccountTypeForm},
  data() {
    return {
      cooperativeAccountType: {
        id: null,
        name: null,
        number: null
      },
      landlordAccountType: {
        id: null,
        name: null,
        number: null
      },
      contractorAccountType: {
        id: null,
        name: null,
        number: null
      },
      isCooperativeAccountFormVisible: false,
      isLandlordAccountFormVisible: false,
      isContractorAccountFormVisible: false,
      invoiceTypeList: [],
      chargeTypeList: []
    }
  },
  methods: {
    loadCooperativeAccountType() {
      this.$loadingPromises([this.loadCooperativeAccountTypeRequest()]);
    },
    loadCooperativeAccountTypeRequest() {
      return AccountTypeService.getCooperativeAccountType()
          .then(response => {
            this.cooperativeAccountType = response.data;
          })
          .catch(error => {
            this.cooperativeAccountType.id = null;
            this.$errorToast(error);
          });
    },
    loadContractorAccountType() {
      this.$loadingPromises([this.loadContractorAccountTypeRequest()]);
    },
    loadContractorAccountTypeRequest() {
      return AccountTypeService.getContractorAccountType()
          .then(response => {
            this.contractorAccountType = response.data;
          })
          .catch(error => {
            this.contractorAccountType.id = null;
            this.$errorToast(error);
          });
    },
    loadLandlordAccountType() {
      this.$loadingPromises([this.loadLandlordAccountTypeRequest()]);
    },
    loadLandlordAccountTypeRequest() {
      return AccountTypeService.getLandlordAccountType()
          .then(response => {
            this.landlordAccountType = response.data;
          })
          .catch(error => {
            this.landlordAccountType.id = null;
            this.$errorToast(error);
          });
    },
    loadAllInvoiceType() {
      this.$loadingPromises([this.loadAllInvoiceTypeRequest()]);
    },
    loadAllInvoiceTypeRequest() {
      return InvoiceTypeService.getAllInvoiceTypes()
          .then(response => {
            this.invoiceTypeList = response.data;
          })
          .catch(error => {
            this.invoiceTypeList = [];
            this.$errorToast(error);
          });
    },
    loadAllChargeTypes() {
      this.$loadingPromises([this.loadAllChargeTypesRequest()]);
    },
    loadAllChargeTypesRequest() {
      return ChargeTypeService.getAllChargeTypes()
          .then(response => {
            this.chargeTypeList = response.data;
          })
          .catch(error => {
            this.chargeTypeList = [];
            this.$errorToast(error);
          });
    },
    openCooperativeAccountForm() {
      this.isCooperativeAccountFormVisible = true;
    },
    openLandlordAccountForm() {
      this.isLandlordAccountFormVisible = true;
    },
    openContractorAccountForm() {
      this.isContractorAccountFormVisible = true;
    },
    updateCooperativeAccount(accountType) {
      this.cooperativeAccountType = accountType;
      this.hideCooperativeAccountForm();
    },
    updateContractorAccount(accountType) {
      this.contractorAccountType = accountType;
      this.hideContractorAccountForm();
    },
    updateLandlordAccount(accountType) {
      this.landlordAccountType = accountType;
      this.hideLandlordAccountForm();
    },
    hideLandlordAccountForm() {
      this.isLandlordAccountFormVisible = false;
    },
    hideCooperativeAccountForm() {
      this.isCooperativeAccountFormVisible = false;
    },
    hideContractorAccountForm() {
      this.isContractorAccountFormVisible = false;
    }
  },
  mounted() {
    this.$loadingPromises([
      this.loadCooperativeAccountTypeRequest(),
      this.loadContractorAccountTypeRequest(),
      this.loadLandlordAccountTypeRequest(),
      this.loadAllInvoiceTypeRequest(),
      this.loadAllChargeTypesRequest()
    ]);
  }
}
</script>

<style scoped>

.text-h5 {
  text-align: center;
}

</style>
