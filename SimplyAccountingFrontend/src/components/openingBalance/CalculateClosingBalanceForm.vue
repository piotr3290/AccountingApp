<template>
  <v-card class="calculate-closing-balance-form">
    <v-card-title class="primary">
      {{ $_.capitalize($t('common.calculateClosingBalance')) }}
    </v-card-title>

    <v-card-text class="mt-4 pb-1">
      <validation-observer ref="observer" v-slot="{handleSubmit}">

        <form id="calculate-closing-balance-form" @submit.prevent="handleSubmit(calculateClosingBalance)">

          <validation-provider
              :name="$t('common.year')"
              :rules="{required: true}"
              v-slot="validationContext">

            <year-select v-model="year"
                         :label="$_.capitalize($t('common.year'))"
                         :errors="validationContext.errors"/>

          </validation-provider>

        </form>
      </validation-observer>
    </v-card-text>

    <v-card-actions class="pb-4">
      <v-spacer/>
      <default-button
          button-type="submit"
          form="calculate-closing-balance-form"
          :text="$_.capitalize($t('common.calculate'))"/>

      <default-button
          @click="cancelForm"
          :text="$_.capitalize($t('common.cancel'))"/>
    </v-card-actions>
  </v-card>
</template>

<script>
import DefaultButton from "@/components/common/buttons/DefaultButton";
import YearSelect from "../common/form/YearSelect";
import OpeningBalanceService from "../../core/service/OpeningBalanceService";

export default {
  name: "CalculateClosingBalanceForm",
  components: {YearSelect, DefaultButton},
  props: ['cooperativeId'],
  data() {
    return {
      year: new Date().getFullYear()
    }
  },
  methods: {
    cancelForm() {
      this.$emit('cancel');
    },
    calculateClosingBalance() {
      this.$loadingPromises([this.calculateClosingBalanceRequest()]);
    },
    calculateClosingBalanceRequest() {
      return OpeningBalanceService.calculateClosingBalancesForCooperative({
        cooperativeId: this.cooperativeId,
        year: this.year
      })
          .then(response => {
            this.$successToast(this.$t('openingBalance.messages.calculated'));
            this.$emit('calculated', response.data);
          })
          .catch(error => {
            this.$errorToast(error);
          });
    },
  }
}
</script>

<style scoped>

</style>
