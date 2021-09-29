<template>
  <v-data-table
      class="elevation-1 cooperative-list-view mb-8"
      :headers="tableHeaders"
      :items="cooperatives"
      :page.sync="currentPageNumber"
      :items-per-page="itemsPerPage"
      :search="search"
      :no-data-text="$t('cooperative.messages.noCooperative')"
      hide-default-footer
      multi-sort
      @click:row="cooperativeDetails"
      @pagination="paginationChange($event)">

    <template #top>
      <v-card-title class="justify-center">
        <div class="text-h4">{{ $_.capitalize($t('cooperative.cooperatives')) }}</div>
      </v-card-title>
      <v-divider/>

      <v-card-title>
        <text-input
            class="mx-4"
            v-model="search"
            :label="$_.capitalize($t('common.search'))"
            :clearable="true"/>
        <add-button @click="createCooperative"/>
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
import CooperativeService from "@/core/service/CooperativeService";
import Pagination from "@/components/common/Pagination";
import TextInput from "@/components/common/form/TextInput";
import AddButton from "@/components/common/buttons/AddButton";

export default {
  name: "CooperativeListView",
  components: {AddButton, TextInput, Pagination},
  data() {
    return {
      currentPageNumber: 1,
      itemsPerPage: 10,
      cooperatives: [],
      totalItems: 0,
      search: null
    }
  },
  computed: {
    totalPages() {
      return Math.ceil(this.totalItems / this.itemsPerPage);
    },
    tableHeaders() {
      return [
        {
          text: this.$_.capitalize(this.$t('cooperative.name')),
          value: 'name'
        },
        {
          text: this.$_.upperCase(this.$t('common.nip')),
          value: 'nip'
        },
        {
          text: this.$_.capitalize(this.$t('common.city')),
          value: 'city'
        },
        {
          text: this.$_.capitalize(this.$t('common.street')),
          value: 'street'
        },
        {
          text: this.$_.capitalize(this.$t('common.houseNumber')),
          value: 'houseNumber'
        },
      ];
    }
  },
  methods: {
    loadCooperatives() {
      this.$loadingPromises([this.loadCooperativesRequest()]);
    },
    loadCooperativesRequest() {
      return CooperativeService.getAllCooperatives()
          .then(response => {
            this.cooperatives = response.data;
            this.totalItems = response.data.length;
          })
          .catch(error => {
            this.cooperatives = [];
            this.$errorToast(error);
          });
    },
    createCooperative() {
      this.$router.push({name: 'cooperativeCreate'});
    },
    cooperativeDetails(cooperative) {
      this.$router.push({
        name: 'cooperative',
        params: {id: cooperative.id}
      });
    },
    paginationChange(pagination) {
      this.totalItems = pagination.itemsLength;
    }
  },
  mounted() {
    this.loadCooperatives();
  }
}
</script>

<style scoped>

/deep/ tbody > tr {
  cursor: pointer !important;
}

</style>