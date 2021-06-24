<template>
  <v-card class="invoice-form">

    <v-card-title class="primary">
      {{ $_.capitalize($t('invoice.invoice')) }}
    </v-card-title>

    <v-card-text class="mt-4 pb-1">
      <div v-if="isFormVisible">
        <validation-observer ref="observer" v-slot="{handleSubmit}">
          <form id="invoice-form" @submit.prevent="handleSubmit(createEditInvoice)">

            <validation-provider
                :name="$t('contractor.contractor')"
                :rules="{required: true}"
                v-slot="validationContext">

              <autocomplete-input
                  v-model="invoice.contractorId"
                  tag-name="contractor"
                  :items="contractors"
                  item-text-property="value"
                  item-value-property="id"
                  :label="$_.capitalize($t('contractor.contractor'))"
                  :hint="$t('invoice.messages.chooseContractor')"
                  :disabled="invoice.id!=null"
                  :errors="validationContext.errors"
                  :clearable="true"/>
            </validation-provider>

            <validation-provider
                :name="$t('invoice.type')"
                :rules="{required: true}"
                v-slot="validationContext">

              <select-input
                  v-model="invoice.invoiceTypeId"
                  tag-name="invoice-type"
                  :items="invoiceTypes"
                  item-text-property="value"
                  item-value-property="id"
                  :label="$_.capitalize($t('invoice.type'))"
                  :hint="$t('invoice.messages.chooseType')"
                  :clearable="true"
                  :errors="validationContext.errors"/>
            </validation-provider>

            <validation-provider
                :name="$t('common.realizationDate')"
                :rules="{required: true}"
                v-slot="validationContext">

              <date-input
                  v-model="invoice.realizationDate"
                  tag-name="realization-date"
                  :errors="validationContext.errors"
                  :label="$_.capitalize($t('common.realizationDate'))"/>
            </validation-provider>

            <validation-provider
                :name="$t('invoice.value')"
                :rules="{required: true}"
                v-slot="validationContext">

              <number-input
                  v-model.number="invoice.value"
                  tag-name="invoice-value"
                  :step="0.01"
                  :errors="validationContext.errors"
                  :label="$_.capitalize($t('invoice.value'))"
                  :number-formatter="currencyFormatter"/>
            </validation-provider>

          </form>
        </validation-observer>
      </div>

      <div v-else-if="id">
        <h2>{{ $t('invoice.messages.notFound') }}</h2>
      </div>

      <div v-else>
        <h2>{{ $t('invoice.messages.noCooperative') }}</h2>
      </div>
    </v-card-text>

    <v-card-actions class="text-right mt-2 mr-2 pb-4">
      <v-spacer/>

      <default-button
          v-if="isFormVisible"
          type="submit"
          form="invoice-form"
          :text="createOrEdit"/>

      <default-button
          class="ml-2"
          @click="exitForm"
          :text="$_.capitalize($t('common.cancel'))"/>
    </v-card-actions>

  </v-card>
</template>

<script>
import InvoiceTypeService from "@/core/service/InvoiceTypeService";
import ContractorService from "@/core/service/ContractorService";
import InvoiceService from "@/core/service/InvoiceService";
import {fixedCurrency} from "@/core/service/FormatterService";
import AutocompleteInput from "@/components/common/form/AutocompleteInput";
import SelectInput from "@/components/common/form/SelectInput";
import DateInput from "@/components/common/form/DateInput";
import NumberInput from "@/components/common/form/NumberInput";
import DefaultButton from "@/components/common/buttons/DefaultButton";

export default {
  name: "InvoiceForm",
  components: {DefaultButton, NumberInput, DateInput, SelectInput, AutocompleteInput},
  props: ['id', 'cooperativeId'],
  data() {
    return {
      invoice: {
        id: null,
        cooperativeId: null,
        contractorId: null,
        value: null,
        realizationDate: null,
        invoiceTypeId: null
      },
      invoiceTypes: [],
      contractors: []
    }
  },
  methods: {
    loadAllInvoiceTypes() {
      this.$loadingPromises([this.loadAllInvoiceTypesRequest()]);
    },
    loadAllInvoiceTypesRequest() {
      return InvoiceTypeService.getAllInvoiceTypes()
          .then(response => {
            this.invoiceTypes = this.$_.map(response.data, type => {
              return {id: type.id, value: type.number + ' ' + type.name};
            });
          })
          .catch(error => {
            this.invoiceTypes = [];
            this.$errorToast(error);
          });
    },
    loadAllContractors() {
      this.$loadingPromises([this.loadAllContractorsRequest()]);
    },
    loadAllContractorsRequest() {
      return ContractorService.getAllContractors()
          .then(response => {
            this.contractors = this.$_.map(response.data, contractor => {
              return {id: contractor.id, value: contractor.name};
            });
          })
          .catch(error => {
            this.contractors = [];
            this.$errorToast(error);
          });
    },
    loadInvoice() {
      this.$loadingPromises([this.loadInvoiceRequest()]);
    },
    loadInvoiceRequest() {
      return InvoiceService.getInvoice(this.id)
          .then(response => {
            this.invoice = response.data;
          })
          .catch(error => {
            this.invoice.id = null;
            this.$errorToast(error);
          });
    },
    createEditInvoice() {
      this.$loadingPromises([this.createEditInvoiceRequest()]);
    },
    createEditInvoiceRequest() {
      if (this.invoice.id == null) {
        return InvoiceService.createInvoice(this.invoice)
            .then(() => {
              this.$successToast(this.$t('invoice.messages.created'));
              this.exitForm();
            })
            .catch(error => {
              this.$errorToast(error);
            });
      } else {
        return InvoiceService.editInvoice(this.invoice)
            .then(() => {
              this.$successToast(this.$t('invoice.messages.edited'));
              this.exitForm();
            })
            .catch(error => {
              this.$errorToast(error);
            });
      }
    },
    currencyFormatter(value) {
      return fixedCurrency(value);
    },
    exitForm() {
      this.$router.go(-1);
    },
  },
  computed: {
    createOrEdit() {
      return this.id != null ? this.$_.capitalize(this.$t('common.edit')) : this.$_.capitalize(this.$t('common.create'));
    },
    isInvoiceFound() {
      return this.invoice.id != null;
    },
    isFormVisible() {
      return this.isInvoiceFound || this.cooperativeId != null;
    }
  },
  mounted() {
    const promises = [this.loadAllContractorsRequest(), this.loadAllInvoiceTypesRequest()];

    if (this.id != null) {
      promises.push(this.loadInvoiceRequest());
    } else {
      this.invoice.cooperativeId = this.cooperativeId;
    }

    this.$loadingPromises(promises);
  },
  watch: {
    id() {
      this.loadInvoice();
    },
    cooperativeId(value) {
      this.invoice.cooperativeId = value;
    }
  }
}
</script>

<style scoped>

</style>