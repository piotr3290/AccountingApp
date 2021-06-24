<template>
  <v-card class="landlord-payment-form">

    <v-card-title class="primary">
      {{ $_.capitalize($t('payment.bank')) + ' - ' + $t('payment.landlordPayment') }}
    </v-card-title>

    <v-card-text class="mt-4 pb-1">
      <div v-if="isFormVisible">
        <validation-observer ref="observer" v-slot="{handleSubmit}">
          <form id="landlord-payment-form" @submit.prevent="handleSubmit(createEditLandlordPayment)">

            <validation-provider
                v-if="id == null"
                :name="$t('building.building')"
                :rules="{required: true}"
                v-slot="validationContext">

              <autocomplete-input
                  v-model="selectedBuilding"
                  tag-name="building"
                  :items="buildings"
                  item-text-property="houseNumber"
                  item-value-property="id"
                  :label="$_.capitalize($t('building.building'))"
                  :hint="$t('rate.messages.chooseBuilding')"
                  :errors="validationContext.errors"/>
            </validation-provider>

            <validation-provider
                v-if="id==null"
                :name="$t('premises.premises')"
                :rules="{required: true}"
                v-slot="validationContext">

              <select-input
                  v-model="landlordPayment.premisesId"
                  tag-name="premises"
                  :items="premises"
                  item-text-property="premisesNumber"
                  item-value-property="id"
                  :label="$_.capitalize($t('premises.premises'))"
                  :hint="$t('rate.messages.choosePremises')"
                  :errors="validationContext.errors"/>
            </validation-provider>

            <validation-provider
                :name="$t('payment.value')"
                :rules="{required: true}"
                v-slot="validationContext">

              <number-input
                  v-model.number="landlordPayment.value"
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
                  v-model="landlordPayment.realizationDate"
                  tag-name="realization-date"
                  :errors="validationContext.errors"
                  :label="$_.capitalize($t('common.realizationDate'))"/>
            </validation-provider>

            <validation-provider
                :name="$t('landlord.landlord')"
                :rules="{required: true}"
                v-slot="validationContext">

              <autocomplete-input
                  v-model="landlordPayment.landlordId"
                  tag-name="landlord"
                  :items="landlords"
                  item-text-property="fullName"
                  item-value-property="id"
                  :label="$_.capitalize($t('landlord.landlord'))"
                  :hint="$t('premisesLandlord.messages.chooseLandlord')"
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
          form="landlord-payment-form"
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
import LandlordPaymentService from "@/core/service/LandlordPaymentService";
import BuildingService from "@/core/service/BuildingService";
import PremisesService from "@/core/service/PremisesService";
import LandlordService from "@/core/service/LandlordService";
import AutocompleteInput from "@/components/common/form/AutocompleteInput";
import NumberInput from "@/components/common/form/NumberInput";
import DateInput from "@/components/common/form/DateInput";
import SelectInput from "@/components/common/form/SelectInput";
import DefaultButton from "@/components/common/buttons/DefaultButton";
import CooperativeService from "@/core/service/CooperativeService";

/**TODO**/
export default {
  name: "LandlordPaymentForm",
  components: {DefaultButton, SelectInput, DateInput, NumberInput, AutocompleteInput},
  props: ['id', 'cooperativeId'],
  data() {
    return {
      landlordPayment: {
        id: null,
        premisesId: null,
        landlordId: null,
        realizationDate: null,
        value: null
      },
      buildings: [],
      premises: [],
      landlords: [],
      selectedBuilding: null,
      cooperative: {
        id: null
      }
    }
  },
  computed: {
    createOrEdit() {
      return this.id != null ? this.$_.capitalize(this.$t('common.edit')) : this.$_.capitalize(this.$t('common.create'));
    },
    isLandlordPaymentFound() {
      return this.landlordPayment.id != null;
    },
    isFormVisible() {
      return this.isLandlordPaymentFound || this.id == null;
    }
  },
  methods: {
    currencyFormatter(value) {
      return fixedCurrency(value);
    },
    loadLandlordPayment() {
      this.$loadingPromises([this.loadLandlordPaymentRequest()]);
    },
    loadLandlordPaymentRequest() {
      return LandlordPaymentService.getLandlordPayment(this.id)
          .then(async response => {
            this.landlordPayment = response.data;
            await Promise.all([this.loadLandlordRequest(), this.loadPremisesCooperativeRequest()]);
          })
          .catch(error => {
            this.landlordPayment.id = null;
            this.$errorToast(error);
          });
    },
    loadAllBuildingsForCooperative() {
      this.$loadingPromises([this.loadAllBuildingsForCooperativeRequest()]);
    },
    loadAllBuildingsForCooperativeRequest() {
      return BuildingService.getAllBuildingsForCooperative(this.cooperativeId)
          .then(response => {
            this.buildings = response.data;
          })
          .catch(error => {
            this.buildings = [];
            this.$errorToast(error);
          });
    },
    loadAllPremisesForBuilding() {
      this.$loadingPromises([this.loadAllPremisesForBuildingRequest()]);
    },
    loadAllPremisesForBuildingRequest() {
      return PremisesService.getAllPremisesForBuilding(this.selectedBuilding)
          .then(response => {
            this.premises = response.data;
          })
          .catch(error => {
            this.premises = [];
            this.$errorToast(error);
          });
    },
    loadAllLandlordsForCooperative() {
      this.$loadingPromises([this.loadAllLandlordsForCooperativeRequest()]);
    },
    loadAllLandlordsForCooperativeRequest() {
      return LandlordService.getAllLandlordsForCooperative(this.cooperativeId)
          .then(response => {
            this.landlords = this.$_.map(response.data, landlord => {
              return {id: landlord.id, fullName: landlord.lastName + ' ' + landlord.firstName}
            });
          })
          .catch(error => {
            this.landlords = [];
            this.$errorToast(error);
          })
    },
    loadPremisesCooperative() {
      this.$loadingPromises([this.loadPremisesCooperativeRequest()]);
    },
    loadPremisesCooperativeRequest() {
      return CooperativeService.getPremisesCooperative(this.landlordPayment.premisesId)
          .then(response => {
            this.cooperative.id = response.data.id;
          })
          .catch(error => {
            this.cooperative.id = null;
            this.$errorToast(error);
          });
    },
    selectedBuildingChange() {
      this.landlordPayment.premisesId = null;
      this.premises = [];
      if (this.selectedBuilding != null) {
        this.loadAllPremisesForBuilding();
      }
    },
    createEditLandlordPayment() {
      this.$loadingPromises([this.createEditLandlordPaymentRequest()]);
    },
    createEditLandlordPaymentRequest() {
      if (this.id == null) {
        return LandlordPaymentService.createLandlordPayment(this.landlordPayment)
            .then(() => {
              this.$successToast(this.$t('payment.messages.created'));
              this.cancelForm();
            })
            .catch(error => {
              this.$errorToast(error);
            });
      } else {
        return LandlordPaymentService.editLandlordPayment(this.landlordPayment)
            .then(() => {
              this.$successToast(this.$t('payment.messages.edited'));
              this.cancelForm();
            })
            .catch(error => {
              this.$errorToast(error);
            });
      }
    },
    loadLandlord() {
      this.$loadingPromises([this.loadLandlordRequest()]);
    },
    loadLandlordRequest() {
      return LandlordService.getLandlord(this.landlordPayment.landlordId)
          .then(response => {
            this.landlords = [{
              id: response.data.id,
              fullName: response.data.lastName + ' ' + response.data.firstName
            }];
          })
          .catch(error => {
            this.landlords = [];
            this.$errorToast(error);
          });
    },
    cancelForm() {
      this.$router.push({
        name: 'cooperativePayments',
        params: {cooperativeId: this.cooperative.id}
      });
    }
  },
  mounted() {
    const promises = [];

    if (this.id == null) {
      this.cooperative.id = this.cooperativeId;
      promises.push(this.loadAllBuildingsForCooperativeRequest());
      promises.push(this.loadAllLandlordsForCooperativeRequest());
    } else {
      promises.push(this.loadLandlordPaymentRequest());
    }

    this.$loadingPromises(promises);
  },
  watch: {
    id() {
      if (this.cooperativeId == null || this.id != null) {
        this.loadLandlordPayment();
      } else {
        this.cooperative.id = this.cooperativeId;
      }
    },
    cooperativeId() {
      if (this.id == null) {
        this.cooperative.id = this.cooperativeId;
        this.selectedBuilding = null;
        this.landlordPayment.premisesId = null;
        this.landlordPayment.landlordId = null;
        this.buildings = [];
        this.premises = [];
        this.landlords = [];
        this.$loadingPromises([this.loadAllBuildingsForCooperativeRequest(), this.loadAllLandlordsForCooperativeRequest()]);
      }
    },
    selectedBuilding() {
      this.selectedBuildingChange();
    }
  }
}
</script>

<style scoped>

</style>