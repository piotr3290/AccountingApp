<template>
  <div>
    <ul>
      <li v-for="area in areasList"
          :key="area.id">
        {{ area.startDate }}
        {{ area.endDate }}
        {{ area.value }}
        <b-button @click="deleteArea(area.id)">x</b-button>
        <b-button :disabled="isFormVisible" @click="editArea(area.id)">
          {{ $_.capitalize($t('common.edit')) }}
        </b-button>
      </li>
    </ul>
    <b-button v-if="!isFormVisible" @click="createArea">{{ $_.capitalize($t('common.add')) }}</b-button>

    <area-create :premises-id="premisesId"
                 :id="areaId"
                 v-if="isFormVisible"
                 @updateAreas="updateAreas($event)"
                 @cancel="hideAreaForm"/>

  </div>
</template>

<script>
import AreaService from "@/core/service/AreaService";
import AreaCreate from "@/components/area/AreaCreate";

export default {
  name: "AreasList",
  components: {AreaCreate},
  props: {
    areas: {
      type: Array,
      required: true
    },
    premisesId: {
      required: true
    }
  },
  data() {
    return {
      isFormVisible: false,
      areasList: [],
      areaId: null
    }
  },
  methods: {
    deleteArea(areaId) {
      this.$store.commit('switchOnLoading');
      AreaService.deleteArea(areaId)
          .then(() => {
            this.$_.remove(this.areas, area =>
                area.id = areaId
            );
            this.$root.$bvToast.toast(this.$t('area.messages.deleted'), {
              title: this.$_.capitalize(this.$t('common.success')),
              toaster: 'b-toaster-top-center',
              variant: 'success'
            });
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
    showAreaForm() {
      this.isFormVisible = true;
    },
    hideAreaForm() {
      this.isFormVisible = false;
    },
    editArea(id) {
      this.areaId = id;
      this.showAreaForm();
    },
    createArea() {
      this.showAreaForm();
    },
    updateAreas(areas) {
      this.areasList = areas;
      this.hideAreaForm();
    }
  },
  mounted() {
    this.areasList = this.areas;
  }
}
</script>

<style scoped>

</style>