<template>
  <v-card class="consumption-form">

    <template v-if="isFormVisible">

      <v-card-title class="primary">
        {{ $_.capitalize($t('consumption.consumption')) }}
      </v-card-title>

      <v-card-text class="mt-4 pb-1">
        <validation-observer ref="observer" v-slot="{handleSubmit}">
          <form id="consumption-form" @submit.prevent="handleSubmit(createEditConsumption)">

            <validation-provider
                :name="$t('consumption.value')"
                :rules="{required: true}"
                v-slot="validationContext">

              <number-input
                  v-model.number="consumption.value"
                  tag-name="consumption-value"
                  :errors="validationContext.errors"
                  :label="$_.capitalize($t('consumption.value'))"/>
            </validation-provider>


            <validation-provider
                :name="$t('common.from')"
                v-slot="validationContext">

              <date-input
                  v-model="consumption.startDate"
                  tag-name="start-date"
                  :errors="validationContext.errors"
                  :label="$_.capitalize($t('common.from'))"
                  :clearable="true"/>
            </validation-provider>

            <validation-provider
                :name="$t('common.to')"
                v-slot="validationContext">
              <date-input
                  v-model="consumption.endDate"
                  tag-name="end-date"
                  :errors="validationContext.errors"
                  :label="$_.capitalize($t('common.to'))"
                  :clearable="true"/>
            </validation-provider>

            <validation-provider
                :name="$t('consumption.chargeType')"
                :rules="{required: true}"
                v-slot="validationContext">

              <select-input
                  v-model="consumption.chargeTypeId"
                  tag-name="charge-type"
                  :items="chargeTypes"
                  item-text-property="name"
                  item-value-property="id"
                  :label="$_.capitalize($t('consumption.chargeType'))"
                  :hint="$t('consumption.messages.chooseChargeType')"
                  :disabled="consumption.id!=null"
                  :errors="validationContext.errors"/>
            </validation-provider>

          </form>
        </validation-observer>
      </v-card-text>

      <v-card-actions class="pb-4">
        <v-spacer/>

        <default-button
            type="submit"
            form="consumption-form"
            :text="createOrEdit"/>

        <default-button
            @click="cancelForm"
            :text="$_.capitalize($t('common.cancel'))"/>
      </v-card-actions>

    </template>

    <template v-else-if="id != null">
      <v-card-title class="error">
        {{ $t('consumption.messages.notFound') }}
      </v-card-title>

      <v-card-actions class="pb-4">
        <v-spacer/>
        <default-button
            @click="cancelForm"
            :text="$_.capitalize($t('common.cancel'))"/>
      </v-card-actions>
    </template>

    <template v-else>
      <v-card-title class="error">
        {{ $t('consumption.messages.noPremises') }}
      </v-card-title>

      <v-card-actions class="pb-4">
        <v-spacer/>
        <default-button
            @click="cancelForm"
            :text="$_.capitalize($t('common.cancel'))"/>
      </v-card-actions>
    </template>

  </v-card>
</template>

<script>
import ConsumptionService from "@/core/service/ConsumptionService";
import ChargeTypeService from "@/core/service/ChargeTypeService";
import DefaultButton from "@/components/common/buttons/DefaultButton";
import NumberInput from "@/components/common/form/NumberInput";
import DateInput from "@/components/common/form/DateInput";
import SelectInput from "@/components/common/form/SelectInput";

export default {
  name: "ConsumptionForm",
  components: {SelectInput, DateInput, NumberInput, DefaultButton},
  props: ['id', 'premisesId'],
  data() {
    return {
      consumption: {
        id: null,
        startDate: null,
        endDate: null,
        value: null,
        premisesId: null,
        chargeTypeId: null
      },
      chargeTypes: []
    }
  },
  computed: {
    createOrEdit() {
      return this.id != null ? this.$_.capitalize(this.$t('common.edit')) : this.$_.capitalize(this.$t('common.create'));
    },
    isConsumptionFound() {
      return this.consumption.id != null;
    },
    isFormVisible() {
      return this.isConsumptionFound || this.premisesId != null;
    }
  },
  methods: {
    cancelForm() {
      this.$emit('cancel');
    },
    createEditConsumption() {
      this.$loadingPromises([this.createEditConsumptionRequest()]);
    },
    createEditConsumptionRequest() {
      if (this.consumption.id == null) {
        return ConsumptionService.createConsumption(this.consumption)
            .then(response => {
              this.$successToast(this.$t('consumption.messages.created'));
              this.$emit('created', response.data);
            })
            .catch(error => {
              this.$errorToast(error);
            });
      } else {
        return ConsumptionService.editConsumption(this.consumption)
            .then(response => {
              this.$successToast(this.$t('consumption.messages.edited'));
              this.$emit('edited', response.data);
            })
            .catch(error => {
              this.$errorToast(error);
            });
      }
    },
    loadConsumption() {
      this.$loadingPromises([this.loadConsumptionRequest()]);
    },
    loadConsumptionRequest() {
      return ConsumptionService.getConsumption(this.id)
          .then(response => {
            this.consumption = response.data;
          })
          .catch(error => {
            this.consumption.id = null;
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
    }
  },
  mounted() {
    const promises = [this.loadAllChargeTypesRequest()];

    if (this.id != null) {
      promises.push(this.loadConsumptionRequest());
    } else {
      this.consumption.premisesId = this.premisesId;
    }
    this.$loadingPromises(promises);
  },
  watch: {
    id() {
      if (this.id != null || this.premisesId == null) {
        this.loadConsumption();
      } else {
        this.consumption.id = null;
      }
    },
    premisesId() {
      this.cancelForm();
    }
  }
}
</script>

<style scoped>

</style>
