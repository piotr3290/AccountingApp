<template>
  <v-card class="calculate-advance-form">
    <v-card-title class="primary">
      {{ $_.capitalize($t('common.calculateAdvance')) }}
    </v-card-title>

    <v-card-text class="mt-4 pb-1">
      <validation-observer ref="observer" v-slot="{handleSubmit}">

        <form id="calculate-advances-form" @submit.prevent="handleSubmit(calculateAdvance)">

          <validation-provider
              :name="$t('common.month')"
              :rules="{required: true}"
              v-slot="validationContext">

            <month-year-input
                :value="yearMonth"
                @input="{}"
                tag-name="year-month"
                :label="$_.capitalize($t('common.month'))"
                :errors="validationContext.errors"
                :month="month"
                :year="year"
                @monthChange="month = $event"
                @yearChange="year = $event"/>
          </validation-provider>

        </form>
      </validation-observer>
    </v-card-text>

    <v-card-actions class="pb-4">
      <v-spacer/>
      <default-button
          button-type="submit"
          form="calculate-advances-form"
          :text="$_.capitalize($t('common.calculate'))"/>

      <default-button
          @click="cancelForm"
          :text="$_.capitalize($t('common.cancel'))"/>
    </v-card-actions>
  </v-card>
</template>

<script>
import AdvanceService from "@/core/service/AdvanceService";
import DefaultButton from "@/components/common/buttons/DefaultButton";
import MonthYearInput from "@/components/common/form/MonthYearInput";
import {monthYearDateString} from "@/core/service/DateService";

export default {
  name: "CalculateAdvanceForm",
  components: {DefaultButton, MonthYearInput},
  props: ['premisesId', 'buildingId', 'cooperativeId'],
  data() {
    return {
      month: new Date().getMonth() + 1,
      year: new Date().getFullYear()
    }
  },
  methods: {
    cancelForm() {
      this.$emit('cancel');
    },
    calculateAdvance() {
      this.$loadingPromises([this.calculateAdvanceRequest()]);
    },
    calculateAdvanceRequest() {
      if (this.premisesId != null) {
        return this.calculatePremisesAdvanceRequest();
      } else if (this.buildingId != null) {
        return this.calculateBuildingAdvanceRequest();
      } else {
        return this.calculateCooperativeAdvanceRequest();
      }
    },
    calculateCooperativeAdvanceRequest() {
      return AdvanceService.calculateAdvancesForCooperative(this.calculateRequestBody(this.cooperativeId))
          .then(response => {
            this.$successToast(this.$t('advance.messages.calculated'));
            this.$emit('calculated', response.data);
          })
          .catch(error => {
            this.$errorToast(error);
          });
    },
    calculateBuildingAdvanceRequest() {
      return AdvanceService.calculateAdvancesForBuilding(this.calculateRequestBody(this.buildingId))
          .then(response => {
            this.$successToast(this.$t('advance.messages.calculated'));
            this.$emit('calculated', response.data);
          })
          .catch(error => {
            this.$errorToast(error);
          });
    },
    calculatePremisesAdvanceRequest() {
      return AdvanceService.calculateAdvancesForPremises(this.calculateRequestBody(this.premisesId))
          .then(response => {
            this.$successToast(this.$t('advance.messages.calculated'));
            this.$emit('calculated', response.data);
          })
          .catch(error => {
            this.$errorToast(error);
          });
    },
    calculateRequestBody(id) {
      return {id: id, year: this.year, month: this.month};
    },
    yearMonth() {
      return monthYearDateString(this.advance.year, this.advance.month);
    }
  }
}
</script>

<style scoped>

</style>