<template>
  <div>
    <b-button @click="createLandlord">{{ $_.capitalize($t('common.add')) }}</b-button>
    <div v-if="isAnyElement">
      <ul>
        <li v-for="landlord in currentPageLandlords"
            :key="landlord.id"
            @click="landlordDetails(landlord.id)">
          {{ landlord.fullName }}
        </li>
      </ul>
    </div>
    <div v-else>
      <h2>{{ $t('landlord.messages.noLandlords') }}</h2>
    </div>
    <b-pagination v-model="currentPageNumber"
                  :total-rows="totalItems"
                  :per-page="itemsPerPage"
                  @change="changePage($event)"/>
  </div>
</template>

<script>
import LandlordService from "@/core/service/LandlordService";

export default {
  name: "LandlordsView",
  data() {
    return {
      landlords: [],
      currentPageNumber: 1,
      totalItems: 0,
      itemsPerPage: 10,
      currentPageLandlords: []
    }
  },
  methods: {
    loadLandlords() {
      this.$store.commit('switchOnLoading');
      LandlordService.getAllLandlords()
          .then(response => {
            this.landlords = response.data;
            this.totalItems = response.data.length;
            this.changePage(this.currentPageNumber);

          })
          .catch(error => {
            this.landlords = [];
            this.currentPageLandlords = [];
            this.totalItems = 0;
            this.$root.$bvToast.toast(error.response.data, {
              title: error.message,
              toaster: 'b-toaster-top-center',
              variant: 'danger'
            });
          })
          .finally(() => {
            this.$store.commit('switchOffLoading');
          });
    },
    changePage(page) {
      this.currentPageLandlords = this.$_.slice(this.landlords, this.startArrayIndex(page), this.endArrayIndex(page));
    },
    startArrayIndex(page) {
      return (page - 1) * this.itemsPerPage;
    },
    endArrayIndex(page) {
      return page * this.itemsPerPage;
    },
    createLandlord() {
      this.$router.push({name: 'landlordCreate'})
    },
    landlordDetails(id) {
      this.$router.push({name: 'landlord', params: {id: id}});
    }
  },
  computed: {
    isAnyElement() {
      return this.totalItems > 0;
    }
  },
  mounted() {
    this.loadLandlords();
  }
}
</script>

<style scoped>

</style>