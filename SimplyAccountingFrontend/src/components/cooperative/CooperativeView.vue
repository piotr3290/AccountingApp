<template>
  <div>
    <div v-if="isCooperativeFound">
      <h2>{{ cooperative.name }}</h2>
      <b-button @click="editCooperative">{{ $_.capitalize($t('common.edit')) }}</b-button>
      <ul v-if="isAnyBuilding">
        <li
            v-for="building in cooperative.buildings"
            :key="building.id"
            @click="loadBuilding(building.id)">
          {{ building.id }}
        </li>
      </ul>
      <h2 v-else>{{ $t('cooperative.messages.noBuilding') }}</h2>
      <b-button @click="createBuilding">{{ $_.capitalize($t('common.add')) }}</b-button>
    </div>
    <div v-else>
      {{ $t("cooperative.messages.notFound") }}
    </div>

  </div>
</template>

<script>
import CooperativeService from "@/core/service/CooperativeService";

export default {
  name: "CooperativeView",
  props: ['id'],
  data() {
    return {
      cooperative: {
        id: null,
        name: String,
        buildings: []
      }
    }
  },
  mounted() {
    this.loadCooperative();
  },
  computed: {
    isAnyBuilding() {
      return this.cooperative.buildings.length > 0;
    },
    isCooperativeFound() {
      return this.cooperative.id != null;
    },
  },
  methods: {
    loadCooperative() {
      this.$store.commit('switchOnLoading');
      CooperativeService.getCooperativesBuildings(this.id)
          .then(response => {
            this.cooperative = response.data;
          })
          .catch(error => {
            this.cooperative.id = null;
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
    createBuilding() {
      this.$router.push({name: 'buildingCreate', params: {cooperativeId: this.id}});
    },
    loadBuilding(buildingId) {
      this.$router.push({name: 'building', params: {id: buildingId}})
    },
    editCooperative() {
      this.$router.push({name: 'cooperativeEdit', params: {id: this.id}})
    }
  },
  watch: {
    id() {
      this.loadCooperative();
    }
  }
}
</script>

<style scoped>

</style>