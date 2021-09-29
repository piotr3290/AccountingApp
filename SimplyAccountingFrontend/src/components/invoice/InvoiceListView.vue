<template>
  <v-data-table
      class="invoice-list-view elevation-1"
      :headers="tableHeaders"
      :items="invoices"
      :page.sync="currentPageNumber"
      :items-per-page="itemsPerPage"
      :no-data-text="$t('invoice.messages.noInvoices')"
      multi-sort
      hide-default-footer>

    <template #top>
      <v-card-title class="text-h4">
        {{ $_.capitalize($t('invoice.invoices')) }}
        <add-button
            class="mx-4"
            @click="createInvoice"/>
        <v-spacer/>
        <default-button
            :text="cooperative.name"
            @click="goToCooperative"/>
      </v-card-title>
      <v-divider/>
    </template>

    <template #item.accountType="{ item }">
      {{ item.invoiceTypeNumber + ' - ' + item.invoiceTypeName }}
    </template>

    <template #item.value="{ item }">
      {{ currency(item.value) }}
    </template>

    <template #item.actions="{ item }">
      <edit-button @click="editInvoice(item.id)"/>
    </template>

    <template #footer>
      <v-divider/>
      <pagination
          class="py-2"
          v-model="currentPageNumber"
          :length="totalPages"/>
    </template>

  </v-data-table>
</template>

<script>
import InvoiceService from "@/core/service/InvoiceService";
import DefaultButton from "@/components/common/buttons/DefaultButton";
import Pagination from "@/components/common/Pagination";
import {currency} from "@/core/service/FormatterService";
import EditButton from "@/components/common/buttons/EditButton";
import CooperativeService from "@/core/service/CooperativeService";
import AddButton from "@/components/common/buttons/AddButton";

export default {
  name: "InvoiceListView",
  components: {AddButton, EditButton, Pagination, DefaultButton},
  props: ['cooperativeId'],
  data() {
    return {
      invoices: [],
      currentPageNumber: 1,
      itemsPerPage: 10,
      currentPage: [],
      cooperative: {
        id: null,
        name: null
      }
    }
  },
  methods: {
    editInvoice(invoiceId) {
      this.$router.push({
        name: 'invoiceEdit',
        params: {id: invoiceId}
      });
    },
    createInvoice() {
      this.$router.push({
        name: 'invoiceCreate',
        params: {cooperativeId: this.cooperativeId}
      });
    },
    loadData() {
      this.$loadingPromises([this.loadCooperativeRequest(), this.getAllInvoicesForCooperativeRequest()]);
    },
    loadCooperative() {
      this.$loadingPromises([this.loadCooperativeRequest()]);
    },
    loadCooperativeRequest() {
      return CooperativeService.getCooperative(this.cooperativeId)
          .then(response => {
            this.cooperative = response.data;
          })
          .catch(error => {
            this.cooperative.id = null;
            this.$errorToast(error);
          });
    },
    getAllInvoicesForCooperative() {
      this.$loadingPromises([this.getAllInvoicesForCooperativeRequest()]);
    },
    getAllInvoicesForCooperativeRequest() {
      return InvoiceService.getAllInvoicesForCooperative(this.cooperativeId)
          .then(response => {
            this.invoices = response.data;
          })
          .catch(error => {
            this.invoices = [];
            this.$errorToast(error);
          })
    },
    currency(value) {
      return currency(value);
    },
    goToCooperative() {
      this.$router.push({name: 'cooperative', params: {id: this.cooperativeId}});
    }
  },
  computed: {
    totalItems() {
      return this.invoices.length;
    },
    totalPages() {
      return Math.ceil(this.totalItems / this.itemsPerPage);
    },
    tableHeaders() {
      return [
        {
          text: this.$_.capitalize(this.$t('common.realizationDate')),
          value: 'realizationDate'
        },
        {
          text: this.$_.capitalize(this.$t('invoice.value')),
          value: 'value'
        },
        {
          text: this.$_.capitalize(this.$t('contractor.contractor')),
          value: 'contractorName'
        },
        {
          text: this.$_.capitalize(this.$t('invoice.accountType')),
          value: 'accountType'
        },
        {
          text: '',
          value: 'actions',
          sortable: false,
          align: 'right'
        },
      ];
    }
  },
  mounted() {
    this.loadData();
  },
  watch: {
    cooperativeId() {
      this.loadData();
    }
  }
}
</script>

<style scoped>

</style>