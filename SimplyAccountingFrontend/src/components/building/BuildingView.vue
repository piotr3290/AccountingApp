<template>
  <div>
    <div v-if="isBuildingFound">
      <h2>{{ building.address }}</h2>
      <b-button @click="editBuilding">{{ $_.capitalize($t('common.edit')) }}</b-button>

      <ul v-if="isAnyPremises">
        <li
            v-for="premises in building.premises"
            :key="premises.id"
            @click="premisesDetails(premises.id)">
          {{ premises.id }}
        </li>
      </ul>
      <h2 v-else>{{ $t('building.messages.noPremises') }}</h2>
    </div>
    <div v-else>
      {{ $t("building.messages.notFound") }}
    </div>
    <b-button @click="createPremises">{{ $_.capitalize($t('common.add')) }}</b-button>
  </div>
</template>

<script>
import BuildingService from "@/core/service/BuildingService";

export default {
  name: "BuildingView",
  props: ['id'],
  data() {
    return {
      building: {
        id: null,
        address: String,
        premises: []
      }
    }
  },
  methods: {
    loadBuilding() {
      this.$store.commit('switchOnLoading');
      BuildingService.getBuildingWithPremises(this.id)
          .then(response => {
            this.building = response.data;
          })
          .catch(error => {
            this.building.id = null
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
    editBuilding() {
      this.$router.push({name: 'buildingEdit', params: {id: this.id}});
    },
    createPremises() {
      this.$router.push({name: 'premisesCreate', params: {buildingId: this.building.id}});
    },
    premisesDetails(premisesId) {
      this.$router.push({name: 'premises', params: {id: premisesId}});
    }
  },
  computed: {
    isAnyPremises() {
      return this.building.premises.length > 0;
    },
    isBuildingFound() {
      return this.building.id != null;
    },
  },
  mounted() {
    this.loadBuilding();
  },
  watch: {
    id() {
      this.loadBuilding();
    }
  }
}
</script>

<style scoped>

</style>