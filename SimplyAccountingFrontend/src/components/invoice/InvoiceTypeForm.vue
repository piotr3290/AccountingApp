<template>
  <div class="invoice-type-form">
    <v-card v-if="isFormVisible">
      <v-card-title class="primary">
        {{ $_.capitalize($t('invoice.accountType')) }}
      </v-card-title>

      <v-card-text class="mt-4 pb-1">
        <validation-observer ref="observer" v-slot="{handleSubmit}">
          <form id="invoice-type-form" @submit.prevent="handleSubmit(createEditInvoiceType)">

            <validation-provider
                :name="$t('invoiceType.name')"
                :rules="{required: true, max: 64}"
                v-slot="validationContext">

              <text-input
                  v-model="invoiceType.name"
                  tag-name="invoice-type-name"
                  :counter="64"
                  :errors="validationContext.errors"
                  :label="$_.capitalize($t('invoiceType.name'))"/>
            </validation-provider>

            <validation-provider
                :name="$t('invoiceType.number')"
                :rules="{required: true, integer: true}"
                v-slot="validationContext">

              <number-input
                  v-model.number="invoiceType.number"
                  tag-name="invoice-type-number"
                  :step="1"
                  :errors="validationContext.errors"
                  :label="$_.capitalize($t('invoiceType.number'))"/>
            </validation-provider>

          </form>
        </validation-observer>
      </v-card-text>

      <v-card-actions class="pb-4">
        <v-spacer/>

        <default-button
            form="invoice-type-form"
            type="submit"
            :text="createOrEdit"/>

        <default-button
            @click="cancelForm"
            :text="$_.capitalize($t('common.cancel'))"/>
      </v-card-actions>
    </v-card>

    <div v-else>
      <h2>{{ $t('invoiceType.messages.notFound') }}</h2>
    </div>

  </div>
</template>

<script>
import InvoiceTypeService from "@/core/service/InvoiceTypeService";
import TextInput from "@/components/common/form/TextInput";
import NumberInput from "@/components/common/form/NumberInput";
import DefaultButton from "@/components/common/buttons/DefaultButton";

export default {
  name: "InvoiceTypeForm",
  components: {DefaultButton, NumberInput, TextInput},
  props: ['id'],
  data() {
    return {
      invoiceType: {
        id: null,
        name: null,
        number: null
      }
    }
  },
  computed: {
    createOrEdit() {
      return this.id != null ? this.$_.capitalize(this.$t('common.edit')) : this.$_.capitalize(this.$t('common.create'));
    },
    isInvoiceTypeFound() {
      return this.invoiceType.id != null;
    },
    isFormVisible() {
      return this.isInvoiceTypeFound || this.id == null;
    }
  },
  methods: {
    loadInvoiceType() {
      this.$loadingPromises([this.loadInvoiceTypeRequest()]);
    },
    loadInvoiceTypeRequest() {
      return InvoiceTypeService.getInvoiceType(this.id)
          .then(response => {
            this.invoiceType = response.data;
          })
          .catch(error => {
            this.invoiceType.id = null;
            this.$errorToast(error);
          });
    },
    createEditInvoiceType() {
      this.$loadingPromises([this.createEditInvoiceTypeRequest()]);
    },
    createEditInvoiceTypeRequest() {
      if (this.id == null) {
        return InvoiceTypeService.createInvoiceType(this.invoiceType)
            .then(response => {
              this.$successToast(this.$t('invoiceType.messages.created'));
              this.$emit('created', response.data);
            })
            .catch(error => {
              this.$errorToast(error);
            });
      } else {
        return InvoiceTypeService.editInvoiceType(this.invoiceType)
            .then(response => {
              this.$successToast(this.$t('invoiceType.messages.edited'));
              this.$emit('edited', response.data);
            })
            .catch(error => {
              this.$errorToast(error);
            });
      }
    },
    cancelForm() {
      this.$emit('cancel');
    }
  },
  mounted() {
    if (this.id != null) {
      this.loadInvoiceType();
    }
  },
  watch: {
    id() {
      if (this.id != null) {
        this.loadInvoiceType();
      } else {
        this.invoiceType.id = null;
      }
    }
  }
}
</script>

<style scoped>

</style>