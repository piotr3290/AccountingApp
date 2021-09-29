<template>
  <v-card class="rate-form">

    <template v-if="isFormVisible">

      <v-card-title class="primary">
        {{ $_.capitalize($t('rate.rate')) }}
        <template v-if="isRateFound">
          <v-spacer/>
          {{ rateInfo }}
        </template>
      </v-card-title>

      <v-card-text class="mt-4 pb-1">
        <validation-observer ref="observer" v-slot="{handleSubmit}">
          <form id="rate-form" @submit.prevent="handleSubmit(createEditRate)">

            <select-input
                v-model="selectedLevel"
                tag-name="level"
                :items="levels"
                item-text-property="name"
                item-value-property="id"
                :label="$_.capitalize($t('rate.level'))"
                :disabled="rate.id != null"/>

            <validation-provider
                v-if="selectedLevel > 0 && !isRateFound"
                :name="$t('cooperative.cooperative')"
                :rules="{required: true}"
                v-slot="validationContext">

              <autocomplete-input
                  v-model="selectedCooperative"
                  tag-name="cooperative"
                  :items="cooperatives"
                  item-text-property="name"
                  item-value-property="id"
                  :label="$_.capitalize($t('cooperative.cooperative'))"
                  :hint="$t('rate.messages.chooseCooperative')"
                  :errors="validationContext.errors"
                  @input="selectedCooperativeChange"/>
            </validation-provider>

            <validation-provider
                v-if="selectedLevel > 1 && !isRateFound"
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
                  :errors="validationContext.errors"
                  @input="selectedBuildingChange"/>
            </validation-provider>

            <validation-provider
                v-if="selectedLevel > 2 && !isRateFound"
                :name="$t('premises.premises')"
                :rules="{required: true}"
                v-slot="validationContext">


              <select-input
                  v-model="selectedPremises"
                  tag-name="premises"
                  :items="premises"
                  item-text-property="premisesNumber"
                  item-value-property="id"
                  :label="$_.capitalize($t('premises.premises'))"
                  :hint="$t('rate.messages.choosePremises')"
                  :errors="validationContext.errors"/>
            </validation-provider>


            <validation-provider
                :name="$t('rate.value')"
                :rules="{required: true}"
                v-slot="validationContext">

              <number-input
                  v-model.number="rate.value"
                  tag-name="rate-value"
                  :errors="validationContext.errors"
                  :label="$_.capitalize($t('rate.value'))"/>
            </validation-provider>

            <validation-provider
                :name="$t('common.from')"
                v-slot="validationContext">

              <date-input
                  v-model="rate.startDate"
                  tag-name="start-date"
                  :errors="validationContext.errors"
                  :label="$_.capitalize($t('common.from'))"
                  :clearable="true"/>
            </validation-provider>

            <validation-provider
                :name="$t('common.to')"
                v-slot="validationContext">

              <date-input
                  v-model="rate.endDate"
                  tag-name="end-date"
                  :errors="validationContext.errors"
                  :label="$_.capitalize($t('common.to'))"
                  :clearable="true"/>
            </validation-provider>

            <validation-provider
                :name="$t('rate.chargeType')"
                :rules="{required: true}"
                v-slot="validationContext">

              <autocomplete-input
                  v-model="rate.chargeTypeId"
                  tag-name="charge-type"
                  :items="chargeTypes"
                  item-text-property="name"
                  item-value-property="id"
                  :label="$_.capitalize($t('rate.chargeType'))"
                  :hint="$t('rate.messages.chooseChargeType')"
                  :disabled="rate.id != null"
                  :errors="validationContext.errors"/>
            </validation-provider>

          </form>
        </validation-observer>
      </v-card-text>

      <v-card-actions class="mt-2 mr-2 pb-4">
        <v-spacer/>
        <default-button
            type="submit"
            form="rate-form"
            :text="createOrEdit"/>

        <default-button
            :text="$_.capitalize($t('common.cancel'))"
            @click="goToRateList"/>

      </v-card-actions>

    </template>

    <template v-else>
      <v-card-title class="error">
        {{ $t('rate.messages.notFound') }}
      </v-card-title>
    </template>

  </v-card>
</template>

<script>
import RateService from "@/core/service/RateService";
import ChargeTypeService from "@/core/service/ChargeTypeService";
import CooperativeService from "@/core/service/CooperativeService";
import BuildingService from "@/core/service/BuildingService";
import PremisesService from "@/core/service/PremisesService";
import SelectInput from "@/components/common/form/SelectInput";
import AutocompleteInput from "@/components/common/form/AutocompleteInput";
import NumberInput from "@/components/common/form/NumberInput";
import DateInput from "@/components/common/form/DateInput";
import DefaultButton from "@/components/common/buttons/DefaultButton";

export default {
  name: "RateForm",
  components: {DefaultButton, DateInput, NumberInput, AutocompleteInput, SelectInput},
  props: ['id'],
  data() {
    return {
      rate: {
        id: null,
        value: null,
        cooperativeId: null,
        buildingId: null,
        premisesId: null,
        chargeTypeId: null,
        startDate: null,
        endDate: null
      },
      chargeTypes: [],
      cooperatives: [],
      buildings: [],
      premises: [],
      selectedLevel: null,
      selectedCooperative: null,
      selectedBuilding: null,
      selectedPremises: null,
      rateInfo: null
    }
  },
  computed: {
    createOrEdit() {
      return this.id != null ? this.$_.capitalize(this.$t('common.edit')) : this.$_.capitalize(this.$t('common.create'));
    },
    isRateFound() {
      return this.rate.id != null;
    },
    isFormVisible() {
      return this.isRateFound || this.id == null;
    },
    levels() {
      return [
        {
          id: 0,
          name: this.$t('rate.levels.GENERAL')
        },
        {
          id: 1,
          name: this.$t('rate.levels.COOPERATIVE')
        },
        {
          id: 2,
          name: this.$t('rate.levels.BUILDING')
        },
        {
          id: 3,
          name: this.$t('rate.levels.PREMISES')
        }
      ]
    },
  },
  methods: {
    goToRateList() {
      this.$router.push({name: 'rates'});
    },
    createEditRate() {
      this.$loadingPromises([this.createEditRateRequest()]);
    },
    createEditRateRequest() {
      if (this.id == null) {

        switch (this.selectedLevel) {
          case 0:
            break;
          case 1:
            this.rate.cooperativeId = this.selectedCooperative;
            break;
          case 2:
            this.rate.buildingId = this.selectedBuilding;
            break;
          case 3:
            this.rate.premisesId = this.selectedPremises;
            break;
          default:
            break;
        }

        return RateService.createRate(this.rate)
            .then(() => {
              this.$successToast(this.$t('rate.messages.created'));
              this.goToRateList();
            })
            .catch(error => {
              this.$errorToast(error);
            });
      } else {
        return RateService.editRate(this.rate)
            .then(() => {
              this.$successToast(this.$t('rate.messages.edited'));
              this.goToRateList();
            })
            .catch(error => {
              this.$errorToast(error);
            });
      }
    },
    loadCooperativeInfoRequest(cooperativeId) {
      return CooperativeService.getCooperative(cooperativeId)
          .then(response => {
            this.rateInfo = response.data.name;
          })
          .catch(error => {
            this.rateInfo = null
            this.$errorToast(error);
          });
    },
    loadBuildingInfoRequest(buildingId) {
      return BuildingService.getBuilding(buildingId)
          .then(response => {
            this.rateInfo = response.data.street + ' ' + response.data.houseNumber + ' - ' + response.data.cooperativeName;
          })
          .catch(error => {
            this.rateInfo = null;
            this.$errorToast(error);
          });
    },
    loadPremisesInfoRequest(premisesId) {
      return PremisesService.getPremisesInfo(premisesId)
          .then(response => {
            this.rateInfo = response.data.premisesNumber + ' ' + response.data.buildingStreet + ' '
                + response.data.buildingHouseNumber + ' - ' + response.data.cooperativeName;
          })
          .catch(error => {
            this.$errorToast(error);
            this.rateInfo = null;
          });
    },
    loadRate() {
      this.$loadingPromises([this.loadRateRequest()]);
    },
    loadRateRequest() {
      return RateService.getRate(this.id)
          .then(async response => {
            this.rate = response.data;
            if (this.rate.cooperativeId != null) {
              this.selectedLevel = 1;
              await this.loadCooperativeInfoRequest(this.rate.cooperativeId);
            } else if (this.rate.buildingId != null) {
              this.selectedLevel = 2;
              await this.loadBuildingInfoRequest(this.rate.buildingId);
            } else if (this.rate.premisesId != null) {
              this.selectedLevel = 3;
              await this.loadPremisesInfoRequest(this.rate.premisesId);
            } else {
              this.selectedLevel = 0;
            }
          })
          .catch(error => {
            this.rate.id = null;
            this.$errorToast(error);
          });
    },
    loadAllChargeTypes() {
      this.$loadingPromises([this.loadAllChargeTypesRequest()]);
    },
    loadAllChargeTypesRequest() {
      return ChargeTypeService.getAllChargeTypes()
          .then(response => {
            this.chargeTypes = this.$_.map(response.data, chargeType => {
              return {
                id: chargeType.id,
                name: chargeType.number + ' ' + chargeType.name
              }
            });
          })
          .catch(error => {
            this.chargeTypes = [];
            this.$errorToast(error);
          });
    },
    loadAllCooperatives() {
      this.$loadingPromises([this.loadAllCooperativesRequest()]);
    },
    loadAllCooperativesRequest() {
      return CooperativeService.getAllCooperatives()
          .then(response => {
            this.cooperatives = response.data;
          })
          .catch(error => {
            this.cooperatives = [];
            this.$errorToast(error);
          });
    },
    selectedCooperativeChange() {
      this.selectedBuilding = null;
      this.selectedPremises = null;
      this.buildings = [];
      this.premises = [];
      if (this.selectedCooperative != null) {
        this.loadAllBuildingsForCooperative();
      }
    },
    loadAllBuildingsForCooperative() {
      this.$loadingPromises([this.loadAllBuildingsForCooperativeRequest()]);
    },
    loadAllBuildingsForCooperativeRequest() {
      return BuildingService.getAllBuildingsForCooperative(this.selectedCooperative)
          .then(response => {
            this.buildings = response.data;
          })
          .catch(error => {
            this.buildings = [];
            this.$errorToast(error);
          });
    },
    selectedBuildingChange() {
      this.selectedPremises = null;
      this.premises = [];
      if (this.selectedBuilding != null) {
        this.loadAllPremisesForBuilding();
      }
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
    }
  },
  created() {
    this.selectedLevel = 0;
  },
  mounted() {
    const promises = [this.loadAllChargeTypesRequest()];

    if (this.id != null) {
      promises.push(this.loadRateRequest());
    } else {
      promises.push(this.loadAllCooperativesRequest());
    }

    this.$loadingPromises(promises);
  },
  watch: {
    id() {
      if (this.id != null) {
        this.loadRate();
      } else {
        this.rate.id = null;
        this.rateInfo = null;
      }
    },
  }
}
</script>

<style scoped>

</style>