<template>
  <v-card class="advance-form">

    <template v-if="isFormVisible">

      <v-card-title class="primary">
        {{ $_.capitalize($t('advance.advance')) }}
      </v-card-title>

      <v-card-text class="mt-4 pb-1">

      <validation-observer ref="observer" v-slot="{handleSubmit}">
        <form id="advance-form" @submit.prevent="handleSubmit(createEditAdvanceRequest)">


          <validation-provider
              :name="$t('common.month')"
              :rules="{required: true}"
              v-slot="validationContext">

            <month-year-input
                :value="yearMonth"
                @input="{}"
                tag-name="year-month"
                :errors="validationContext.errors"
                :label="$_.capitalize($t('common.month'))"
                :month="advance.month"
                :year="advance.year"
                :disabled="advance.id!=null"
                @monthChange="advance.month = $event"
                @yearChange="advance.year = $event"/>
          </validation-provider>

          <validation-provider
              :name="$t('advance.amount')"
              :rules="{required: true}"
              v-slot="validationContext">

            <number-input
                v-model.number="advance.amount"
                tag-name="amount"
                :step="0.01"
                :errors="validationContext.errors"
                :label="$_.capitalize($t('advance.amount'))"
                :number-formatter="currencyFormatter"/>
          </validation-provider>

          <validation-provider
              :name="$t('common.realizationDate')"
              :rules="{required: true}"
              v-slot="validationContext">

            <date-input
                v-model="advance.realizationDate"
                tag-name="realization-date"
                :errors="validationContext.errors"
                :label="$_.capitalize($t('common.realizationDate'))"/>
          </validation-provider>

          <validation-provider
              :name="$t('advance.chargeType')"
              :rules="{required: true}"
              v-slot="validationContext">

            <autocomplete-input
                v-model="advance.chargeTypeId"
                tag-name="charge-type"
                :items="chargeTypeList"
                item-text-property="name"
                item-value-property="id"
                :label="$_.capitalize($t('advance.chargeType'))"
                :hint="$t('advance.messages.chooseChargeType')"
                :disabled="advance.id!=null"
                :errors="validationContext.errors"
                :clearable="true"/>
          </validation-provider>

          <validation-provider
              :name="$t('landlord.landlord')"
              :rules="{required: true}"
              v-slot="validationContext">

            <autocomplete-input
                v-model="advance.landlordId"
                tag-name="landlord"
                :items="landlordList"
                item-text-property="fullName"
                item-value-property="id"
                :label="$_.capitalize($t('landlord.landlord'))"
                :hint="$t('advance.messages.chooseLandlord')"
                :disabled="advance.id!=null"
                :errors="validationContext.errors"
                :clearable="true"/>
          </validation-provider>

        </form>
      </validation-observer>
      </v-card-text>

      <v-card-actions class="pb-4">
        <v-spacer/>

        <default-button
            button-type="submit"
            form="advance-form"
            :text="createOrEdit"/>

        <default-button
            :text="$_.capitalize($t('common.cancel'))"
            @click="cancelForm"/>
      </v-card-actions>
    </template>

    <template v-else>
      <v-card-title class="error">
        {{ $t('advance.messages.notFound') }}
      </v-card-title>
    </template>

  </v-card>
</template>

<script>
import {fixedCurrency} from "@/core/service/FormatterService";
import AdvanceService from "@/core/service/AdvanceService";
import LandlordService from "@/core/service/LandlordService";
import PremisesService from "@/core/service/PremisesService";
import ChargeTypeService from "@/core/service/ChargeTypeService";
import DefaultButton from "@/components/common/buttons/DefaultButton";
import AutocompleteInput from "@/components/common/form/AutocompleteInput";
import NumberInput from "@/components/common/form/NumberInput";
import MonthYearInput from "@/components/common/form/MonthYearInput";
import DateInput from "@/components/common/form/DateInput";
import {monthYearDateString} from "@/core/service/DateService";


export default {
  name: "AdvanceForm",
  components: {DateInput, MonthYearInput, NumberInput, AutocompleteInput, DefaultButton},
  props: ['id', 'premisesId'],
  data() {
    return {
      advance: {
        id: null,
        realizationDate: null,
        amount: null,
        month: new Date().getMonth() + 1,
        year: new Date().getFullYear(),
        chargeTypeId: null,
        landlordId: null,
        premisesId: null
      },
      landlordList: [],
      chargeTypeList: [],
      premises: null,
    }
  },
  computed: {
    createOrEdit() {
      return this.id != null ? this.$_.capitalize(this.$t('common.edit')) : this.$_.capitalize(this.$t('common.create'));
    },
    isAdvanceFound() {
      return this.advance.id != null;
    },
    isFormVisible() {
      return this.isAdvanceFound || this.id == null;
    },
    yearMonth() {
      return monthYearDateString(this.advance.year, this.advance.month);
    }
  },
  methods: {
    currencyFormatter(value) {
      return fixedCurrency(value);
    },
    loadAdvance() {
      this.$loadingPromises([this.loadAdvanceRequest()]);
    },
    loadAdvanceRequest() {
      return AdvanceService.getAdvance(this.id)
          .then(async response => {
            this.advance = response.data;
            await Promise.all([this.loadLandlordsRequest(), this.loadPremisesRequest()]);
          })
          .catch(error => {
            this.advance.id = null;
            this.$errorToast(error);
          });
    },
    loadLandlords() {
      this.$loadingPromises([this.loadLandlordsRequest()]);
    },
    loadLandlordsRequest() {
      return LandlordService.getAllLandlordsForPremises(this.advance.premisesId)
          .then(response => {
            this.landlordList = this.$_.map(response.data, landlord => {
              return {id: landlord.id, fullName: landlord.lastName + ' ' + landlord.firstName}
            });
          })
          .catch(error => {
            this.landlordList = [];
            this.$errorToast(error);
          });
    },
    loadPremises() {
      this.$loadingPromises([this.loadPremisesRequest()]);
    },
    loadPremisesRequest() {
      return PremisesService.getPremises(this.advance.premisesId)
          .then(response => {
            this.premises = response.data;
            this.advance.premisesId = response.data.id;
          })
          .catch(error => {
            this.$errorToast(error);
            this.premises = null;
            this.advance.premisesId = null;
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
    createEditAdvance() {
      this.$loadingPromises([this.createEditAdvanceRequest()]);
    },
    createEditAdvanceRequest() {
      if (this.id == null) {
        return AdvanceService.createAdvance(this.advance)
            .then(() => {
              this.$successToast(this.$t('advance.messages.created'));
              this.cancelForm();
            })
            .catch(error => {
              this.$errorToast(error);
            });
      } else {
        return AdvanceService.editAdvance(this.advance)
            .then(() => {
              this.$successToast(this.$t('advance.messages.edited'));
              this.cancelForm();
            })
            .catch(error => {
              this.$errorToast(error);
            });
      }
    },
    cancelForm() {
      this.$router.push({
        name: 'advancePremises',
        params: {premisesId: this.advance.premisesId}
      });
    },
    premisesIdChange() {
      this.advance.premisesId = this.premisesId;
      this.advance.landlordId = null;
      this.$loadingPromises([this.loadPremisesRequest(), this.loadLandlordsRequest()]);
    }
  },
  mounted() {
    const promises = [this.loadAllChargeTypesRequest()];

    if (this.id == null) {
      this.advance.premisesId = this.premisesId;
      promises.push(this.loadLandlordsRequest());
      promises.push(this.loadPremisesRequest());
    } else {
      promises.push(this.loadAdvanceRequest());
    }

    this.$loadingPromises(promises);
  },
  watch: {
    id() {
      if (this.id == null) {
        this.advance.id = null;
        this.premisesIdChange();
      } else {
        this.loadAdvance();
      }
    },
    premisesId() {
      if (this.id == null) {
        this.premisesIdChange();
      }
    }
  }
}
</script>

<style scoped>

</style>