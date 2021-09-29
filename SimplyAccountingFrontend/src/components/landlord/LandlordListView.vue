<template>
  <v-data-table
      class="landlord-list-view elevation-1"
      :headers="tableHeaders"
      :items="landlords"
      :no-data-text="$t('landlord.messages.noLandlords')"
      :search="search"
      hide-default-footer
      multi-sort
      @click:row="goToLandlordView">

    <template #top>
      <v-card-title class="text-h4 justify-center">
        {{ $_.capitalize($t('landlord.landlords')) }}
      </v-card-title>
      <v-divider/>
      <v-card-title>
        <text-input
            class="mx-4"
            v-model="search"
            :label="$_.capitalize($t('common.search'))"
            :clearable="true"/>
        <add-button @click="createLandlord"/>
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
import LandlordService from "@/core/service/LandlordService";
import Pagination from "@/components/common/Pagination";
import TextInput from "@/components/common/form/TextInput";
import AddButton from "@/components/common/buttons/AddButton";

export default {
  name: "LandlordListView",
  components: {AddButton, TextInput, Pagination},
  data() {
    return {
      landlords: [],
      currentPageNumber: 1,
      itemsPerPage: 10,
      search: null
    }
  },
  methods: {
    loadLandlords() {
      this.$loadingPromises([this.loadLandlordsRequest()]);
    },
    loadLandlordsRequest() {
      return LandlordService.getAllLandlords()
          .then(response => {
            this.landlords = response.data;
          })
          .catch(error => {
            this.landlords = [];
            this.$errorToast(error);
          });
    },
    createLandlord() {
      this.$router.push({name: 'landlordCreate'})
    },
    goToLandlordView(landlord) {
      this.$router.push({
        name: 'landlord',
        params: {id: landlord.id}
      });
    }
  },
  computed: {
    totalItems() {
      return this.landlords.length;
    },
    totalPages() {
      return Math.ceil(this.totalItems / this.itemsPerPage);
    },
    tableHeaders() {
      return [
        {
          text: this.$_.capitalize(this.$t('landlord.landlord')),
          value: 'fullName'
        },
        {
          text: this.$_.capitalize(this.$t('common.address')),
          value: 'address'
        },
        {
          text: this.$_.capitalize(this.$t('common.phoneNumber')),
          value: 'phoneNumber'
        },
      ];
    }
  },
  mounted() {
    this.loadLandlords();
  }
}
</script>

<style scoped>

/deep/ tbody > tr {
  cursor: pointer !important;
}

</style>