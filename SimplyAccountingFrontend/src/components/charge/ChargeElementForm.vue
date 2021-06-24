<template>
  <v-card class="charge-element-form">
    <v-card-title class="primary">
      {{ $_.capitalize($t('charge.element')) }}
    </v-card-title>
    <v-card-text class="mt-4 pb-1">
      <div v-if="isFormVisible">
        <validation-observer ref="observer" v-slot="{handleSubmit}">
          <form id="charge-element-form" @submit.prevent="handleSubmit(createEditChargeElement)">

            <validation-provider
                :name="$t('charge.type')"
                :rules="{required: true}"
                v-slot="validationContext">

              <autocomplete-input
                  v-model="chargeElement.chargeTypeId"
                  tag-name="charge-type"
                  :items="chargeTypes"
                  item-text-property="name"
                  item-value-property="id"
                  :label="$_.capitalize($t('charge.type'))"
                  :hint="$t('charge.messages.chooseType')"
                  :disabled="chargeElement.id!=null"
                  :errors="validationContext.errors"/>
            </validation-provider>

            <validation-provider
                :name="$t('premises.type')"
                v-slot="validationContext">

              <select-input
                  v-model="chargeElement.premisesTypeId"
                  tag-name="premises-type"
                  :items="premisesTypes"
                  item-text-property="name"
                  item-value-property="id"
                  :label="$_.capitalize($t('premises.type'))"
                  :errors="validationContext.errors"
                  :clearable="true"/>
            </validation-provider>


            <validation-provider
                :name="$t('common.from')"
                v-slot="validationContext">

              <date-input
                  v-model="chargeElement.startDate"
                  tag-name="start-date"
                  :errors="validationContext.errors"
                  :label="$_.capitalize($t('common.from'))"
                  :clearable="true"/>
            </validation-provider>

            <validation-provider
                :name="$t('common.to')"
                v-slot="validationContext">

              <date-input
                  v-model="chargeElement.endDate"
                  tag-name="end-date"
                  :errors="validationContext.errors"
                  :label="$_.capitalize($t('common.to'))"
                  :clearable="true"/>
            </validation-provider>

            <div class="text-h6">{{ $_.capitalize($t('charge.formula')) + ': ' +formula }}</div>

            <v-row>
              <v-col cols="6" class="col-6">
                <checkbox-input
                    tag-name="people-number-multiply"
                    v-model="chargeElement.multiplyPeopleNumber"
                    :label="$t('peopleNumber.number')">
                </checkbox-input>

                <checkbox-input
                    tag-name="consumption-multiply"
                    v-model="chargeElement.multiplyConsumption"
                    :label="$t('consumption.consumption')">
                </checkbox-input>
              </v-col>

              <v-col cols="6" class="col-6">
                <checkbox-input
                    tag-name="area-multiply"
                    v-model="multiplyArea"
                    :label="$t('area.surface')">
                </checkbox-input>

                <validation-provider
                    :rules="{required: multiplyArea}"
                    :name="$t('area.surface')"
                    v-slot="validationContext">

                  <select-input
                      v-model="chargeElement.areaTypeId"
                      tag-name="area-type"
                      :items="areaTypes"
                      item-text-property="name"
                      item-value-property="id"
                      :label="$_.capitalize($t('area.type'))"
                      :errors="validationContext.errors"
                      :clearable="true"
                      :disabled="!multiplyArea"/>
                </validation-provider>
              </v-col>

            </v-row>

          </form>
        </validation-observer>
      </div>

      <div v-else-if="id != null">
        <h3>{{ $t('charge.messages.notFound') }}</h3>
      </div>

      <div v-else>
        <h3>{{ $t('charge.messages.loadingError') }}</h3>
      </div>
    </v-card-text>

    <v-card-actions class="mt-2 mr-2 pb-4">
      <v-spacer/>

      <default-button
          v-if="isFormVisible"
          form="charge-element-form"
          type="submit"
          :text="createOrEdit"/>

      <default-button
          @click="cancelForm"
          :text="$_.capitalize($t('common.cancel'))"/>
    </v-card-actions>


  </v-card>
</template>

<script>
import ChargeElementService from "@/core/service/ChargeElementService";
import AreaTypeService from "@/core/service/AreaTypeService";
import PremisesTypeService from "@/core/service/PremisesTypeService";
import ChargeTypeService from "@/core/service/ChargeTypeService";
import DefaultButton from "@/components/common/buttons/DefaultButton";
import AutocompleteInput from "@/components/common/form/AutocompleteInput";
import SelectInput from "@/components/common/form/SelectInput";
import DateInput from "@/components/common/form/DateInput";
import CheckboxInput from "@/components/common/form/CheckboxInput";

export default {
  name: "ChargeElementForm",
  components: {CheckboxInput, DateInput, SelectInput, AutocompleteInput, DefaultButton},
  props: ['id', 'cooperativeId'],
  data() {
    return {
      chargeElement: {
        id: null,
        cooperativeId: null,
        chargeTypeId: null,
        startDate: null,
        endDate: null,
        multiplyPeopleNumber: false,
        multiplyConsumption: false,
        areaTypeId: null,
        premisesTypeId: null
      },
      multiplyArea: false,
      premisesTypes: [],
      areaTypes: [],
      chargeTypes: []
    }
  },
  computed: {
    isChargeElementFound() {
      return this.chargeElement.id != null;
    },
    isCooperativeFound() {
      return this.chargeElement.cooperativeId != null;
    },
    isFormVisible() {
      return this.isChargeElementFound || this.isCooperativeFound;
    },
    formula() {
      let formula = this.$t('rate.rate');
      if (this.chargeElement.multiplyPeopleNumber) {
        formula = formula + ' * ' + this.$t('peopleNumber.number');
      }
      if (this.chargeElement.multiplyConsumption) {
        formula = formula + ' * ' + this.$t('consumption.consumption');
      }
      if (this.multiplyArea) {
        formula = formula + ' * ' + this.$t('area.surface');
      }
      return formula;
    },
    createOrEdit() {
      return this.id != null ? this.$_.capitalize(this.$t('common.edit')) : this.$_.capitalize(this.$t('common.create'));
    },
    allPremisesType() {
      return {id: null, name: this.$_.capitalize(this.$t('common.all'))};
    }
  },
  methods: {
    areaMultiplyChange(doMultiplyArea) {
      if (!doMultiplyArea) {
        this.chargeElement.areaTypeId = null;
      }
    },
    createEditChargeElement() {
      return this.$loadingPromises([this.createEditChargeElementRequest()]);
    },
    createEditChargeElementRequest() {
      if (this.id == null) {
        return ChargeElementService.createChargeElement(this.chargeElement)
            .then(response => {
              this.$successToast(this.$t('charge.messages.created'));
              this.goBackToCooperativeChargesPlan(response.data.cooperativeId);
            })
            .catch(error => {
              this.$errorToast(error);
            });
      } else {
        return ChargeElementService.editChargeElement(this.chargeElement)
            .then(response => {
              this.$successToast(this.$t('charge.messages.edited'));
              this.goBackToCooperativeChargesPlan(response.data.cooperativeId);
            })
            .catch(error => {
              this.$errorToast(error);
            });
      }
    },
    loadChargeElement() {
      this.$loadingPromises([this.loadChargeElementRequest()]);
    },
    loadChargeElementRequest() {
      return ChargeElementService.getChargeElement(this.id)
          .then(response => {
            this.chargeElement = response.data;
          })
          .catch(error => {
            this.chargeElement.id = null;
            this.$errorToast(error);
          });
    },
    loadAllAreaTypes() {
      this.$loadingPromises([this.loadAllAreaTypesRequest()]);
    },
    loadAllAreaTypesRequest() {
      return AreaTypeService.getAllAreaTypes()
          .then(response => {
            this.areaTypes = response.data;
          })
          .catch(error => {
            this.areaTypes = [];
            this.$errorToast(error);
          });
    },
    loadAllPremisesTypes() {
      this.$loadingPromises([this.loadAllPremisesTypesRequest()]);
    },
    loadAllPremisesTypesRequest() {
      return PremisesTypeService.getAllPremisesTypes()
          .then(response => {
            this.premisesTypes = response.data;
            this.premisesTypes.unshift(this.allPremisesType);
          })
          .catch(error => {
            this.premisesTypes = [this.allPremisesType];
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
              return {id: chargeType.id, name: chargeType.number + ' ' + chargeType.name}
            });
          })
          .catch(error => {
            this.chargeTypes = [];
            this.$errorToast(error);
          });
    },
    goBackToCooperativeChargesPlan(cooperativeId) {
      this.$router.push({
        name: 'chargesPlan',
        params: {cooperativeId: cooperativeId}
      });
    },
    cancelForm() {
      this.goBackToCooperativeChargesPlan(this.chargeElement.cooperativeId);
    }
  },
  mounted() {
    const promises = [this.loadAllAreaTypesRequest(), this.loadAllChargeTypesRequest(), this.loadAllPremisesTypesRequest()];

    if (this.id != null) {
      promises.push(this.loadChargeElementRequest());
    } else {
      this.chargeElement.cooperativeId = this.cooperativeId;
    }
    this.$loadingPromises(promises);
  },
  watch: {
    id() {
      this.loadChargeElement();
    },
    cooperativeId() {
      if (this.id == null) {
        this.chargeElement.cooperativeId = this.cooperativeId;
      }
    },
    multiplyArea(value) {
      this.areaMultiplyChange(value);
    }
  }
}
</script>

<style scoped>

</style>