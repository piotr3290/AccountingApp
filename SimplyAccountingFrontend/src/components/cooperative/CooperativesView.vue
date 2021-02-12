<template>
  <div class="cooperatives-view">
    <h1>COOPERATIVES</h1>
    <b-button @click="createCooperative">Create</b-button>
    <ul v-if="isAnyElement">
      <li v-for="item in cooperatives" :key="item.id" @click="cooperativeDetails(item.id)">
        {{ item.name }}
      </li>
    </ul>
    <h1 v-else>{{$t('cooperative.messages.noCooperative')}}</h1>
    <b-pagination v-model="currentPageNumber"
                  :total-rows="totalItems"
                  :per-page="itemsPerPage"
                  @change="changePage($event)"/>
  </div>
</template>

<script>
import CooperativeService from "@/core/service/CooperativeService";

export default {
  name: "CooperativesView",
  data() {
    return {
      currentPageNumber: 1,
      totalItems: 0,
      itemsPerPage: 10,
      cooperatives: []
    }
  },
  computed: {
    isAnyElement() {
      return this.totalItems > 0;
    }
  },
  methods: {
    changePage(page) {
      this.$store.commit('switchOnLoading');
      CooperativeService.getCooperatives(page, this.itemsPerPage)
          .then(response => {
            this.totalItems = response.data.totalElements;
            this.itemsPerPage = response.data.size;
            this.cooperatives = response.data.content;
          })
          .catch(error => {
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
    createCooperative() {
      this.$router.push({name: 'cooperativeCreate'});
    },
    cooperativeDetails(id) {
      this.$router.push({name: 'cooperative', params: {id: id}});
    }
  },
  mounted() {
    this.changePage(1);
  }
}
</script>

<style scoped>

</style>