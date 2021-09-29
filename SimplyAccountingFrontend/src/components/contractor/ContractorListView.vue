<template>
  <v-data-table
      class="contractor-list-view elevation-1"
      :headers="tableHeaders"
      :items="contractors"
      :no-data-text="$t('contractor.messages.noContractors')"
      :search="search"
      hide-default-footer
      multi-sort
      @click:row="goToContractor">

    <template #top>
      <v-card-title class="text-h4 justify-center">
        {{ $_.capitalize($t('contractor.contractors')) }}
      </v-card-title>
      <v-divider/>
      <v-card-title>
        <text-input
            class="mx-4"
            v-model="search"
            :label="$_.capitalize($t('common.search'))"
            :clearable="true"/>
        <add-button @click="createContractor"/>
        <v-spacer/>
      </v-card-title>
      <v-divider/>
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
import ContractorService from "@/core/service/ContractorService";
import Pagination from "@/components/common/Pagination";
import TextInput from "@/components/common/form/TextInput";
import AddButton from "@/components/common/buttons/AddButton";

export default {
  name: "ContractorListView",
  components: {AddButton, TextInput, Pagination},
  data() {
    return {
      contractors: [],
      currentPageNumber: 1,
      itemsPerPage: 10,
      search: null
    }
  },
  computed: {
    totalPages() {
      return Math.ceil(this.totalItems / this.itemsPerPage);
    },
    totalItems() {
      return this.contractors.length;
    },
    tableHeaders() {
      return [
        {
          text: this.$_.capitalize(this.$t('contractor.contractor')),
          value: 'name'
        },
        {
          text: this.$_.capitalize(this.$t('common.nip')),
          value: 'nip'
        },
      ];
    }
  },
  methods: {
    loadContractors() {
      this.$loadingPromises([this.loadContractorsRequest()]);
    },
    loadContractorsRequest() {
      return ContractorService.getAllContractors()
          .then(response => {
            this.contractors = response.data;
          })
          .catch(error => {
            this.contractors = [];
            this.$errorToast(error);
          });
    },
    goToContractor(contractor) {
      this.$router.push({
        name: 'contractor',
        params: {id: contractor.id}
      });
    },
    createContractor() {
      this.$router.push({
        name: 'contractorCreate'
      });
    }
  },
  mounted() {
    this.loadContractors();
  }
}
</script>

<style scoped>

/deep/ tbody > tr {
  cursor: pointer !important;
}

</style>