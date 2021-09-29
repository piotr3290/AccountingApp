<template>
  <div class="invoice-type-list">

    <v-list class="py-0" v-if="isListVisible">

      <template v-for="(invoiceType, index) in invoiceTypeList">

        <v-list-item :key="invoiceType.id">
          <v-list-item-title>
            {{ invoiceType.number + ' - ' + invoiceType.name }}
          </v-list-item-title>

          <edit-button
              class="ml-1"
              :disabled="isFormVisible"
              @click="editInvoiceType(invoiceType.id)"/>

        </v-list-item>

        <v-divider :key="index.toString() + '-' + invoiceType.id"/>

      </template>
    </v-list>

    <div v-else>
      {{ $t('invoiceType.messages.noInvoiceType') }}
    </div>

    <div class="mt-3 text-center">
      <add-button
          :disabled="isFormVisible"
          @click="createInvoiceType"/>
    </div>

    <v-dialog
        v-model="isFormVisible"
        max-width="400px">
      <invoice-type-form
          :id="invoiceTypeId"
          @cancel="hideForm"
          @created="updateInvoiceTypeList($event)"
          @edited="updateInvoiceTypeList($event)"/>
    </v-dialog>


  </div>
</template>

<script>
import InvoiceTypeForm from "@/components/invoice/InvoiceTypeForm";
import AddButton from "@/components/common/buttons/AddButton";
import EditButton from "@/components/common/buttons/EditButton";

export default {
  name: "InvoiceTypeList",
  components: {EditButton, AddButton, InvoiceTypeForm},
  props: {
    invoiceTypes: {
      type: Array,
      required: true
    }
  },
  data() {
    return {
      invoiceTypeList: [],
      isFormVisible: false,
      invoiceTypeId: null
    }

  },
  computed: {
    isListVisible() {
      return this.invoiceTypeList.length > 0;
    }
  },
  methods: {
    showForm() {
      this.isFormVisible = true;
    },
    hideForm() {
      this.isFormVisible = false;
    },
    editInvoiceType(id) {
      this.invoiceTypeId = id;
      this.showForm();
    },
    createInvoiceType() {
      this.showForm();
    },
    updateInvoiceTypeList(invoiceTypeList) {
      this.invoiceTypeList = invoiceTypeList;
      this.hideForm();
    }
  },
  mounted() {
    this.invoiceTypeList = this.invoiceTypes;
  },
  watch: {
    invoiceTypes() {
      this.invoiceTypeList = this.invoiceTypes;
    },
    isFormVisible() {
      if (this.isFormVisible === false) {
        this.invoiceTypeId = null;
      }
    }
  }
}
</script>

<style scoped>

</style>