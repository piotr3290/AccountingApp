<template>
  <div>
    <div v-if="isPremisesFound">
      <h2>{{ premises.premisesNumber }}</h2>
      <h2>{{ premises.premisesTypeName }}</h2>
      <b-button @click="editPremises">{{ $_.capitalize($t('common.edit')) }}</b-button>
      <areas-list :areas="premises.areas" :premises-id="premises.id"/>
    </div>
    <div v-else>
      <h2>{{ $t('premises.messages.notFound') }}</h2>
    </div>
  </div>
</template>

<script>
import PremisesService from "@/core/service/PremisesService";
import AreasList from "@/components/area/AreasList";

export default {
  name: "PremisesView",
  components: {AreasList},
  props: ['id'],
  data() {
    return {
      premises: {
        id: null,
        premisesNumber: null,
        premisesTypeName: null,
        areas: []
      }
    }
  },
  methods: {
    loadPremises() {
      this.$store.commit('switchOnLoading');
      PremisesService.getPremisesView(this.id)
          .then(response => {
            this.premises = response.data;
          })
          .catch(error => {
            this.premises.id = null;
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
    editPremises() {
      this.$router.push({name: 'premisesEdit', params: {id: this.premises.id}})
    }
  },
  computed: {
    isPremisesFound() {
      return this.premises.id != null;
    }
  },
  mounted() {
    this.loadPremises();
  },
  watch: {
    id() {
      this.loadPremises();
    }
  }
}
</script>

<style scoped>

</style>