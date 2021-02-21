<template>
  <div>
    <b-button @click="createLandlord">{{ $_.capitalize($t('common.add')) }}</b-button>
    <div v-if="isAnyElement">
      <ul>
        <li v-for="contractor in currentPageContractors"
            :key="contractor.id"
            @click="contractorDetails(contractor.id)">
          {{ contractor.name }}
        </li>
      </ul>
    </div>
    <div v-else>
      <h2>{{ $t('contractor.messages.noContractors') }}</h2>
    </div>
    <b-pagination v-model="currentPageNumber"
                  :total-rows="totalItems"
                  :per-page="itemsPerPage"
                  @change="changePage($event)"/>
  </div>
</template>

<script>
import ContractorService from "@/core/service/ContractorService";

export default {
  name: "ContractorsView",
  data() {
    return {
      contractors: [],
      currentPageNumber: 1,
      totalItems: 0,
      itemsPerPage: 10,
      currentPageContractors: []
    }
  },
  computed: {
    isAnyElement() {
      return this.totalItems > 0;
    }
  },
  methods: {
    loadContractors() {
      ContractorService.getAllContractors()
          .then(response => {
            this.contractors = response.data;
            this.totalItems = response.data.length;
            this.changePage(this.currentPageNumber);
          })
          .catch(error => {
            this.contractors = [];
            this.currentPageContractors = [];
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
      this.currentPageContractors = this.$_.slice(this.contractors, this.startArrayIndex(page), this.endArrayIndex(page));
    },
    startArrayIndex(page) {
      return (page - 1) * this.itemsPerPage;
    },
    endArrayIndex(page) {
      return page * this.itemsPerPage;
    },
    contractorDetails(id) {
      this.$router.push({name: 'contractor', params: {id: id}});
    },
    createLandlord() {
      this.$router.push({name: 'contractorCreate'});
    }
  },
  mounted() {
    this.loadContractors();
  }
}
</script>

<style scoped>

</style>