<template>
  <div>
    <div v-if="isLandlordFound">
      <h2>{{ landlord.fullName }}</h2>
      <h2>{{ landlord.address }}</h2>
      <h2>{{ landlord.phoneNumber + ' ' + landlord.addressEmail }}</h2>
      <b-button @click="editLandlord">{{ $_.capitalize($t('common.edit')) }}</b-button>
    </div>
    <div v-else>
      {{ $t("landlord.messages.notFound") }}
    </div>
  </div>
</template>

<script>
import LandlordService from "@/core/service/LandlordService";

export default {
  name: "LandlordView",
  props: ['id'],
  data() {
    return {
      landlord: {
        id: null,
        fullName: null,
        address: null,
        phoneNumber: null,
        addressEmail: null
      }
    }
  },
  methods: {
    loadLandlord() {
      this.$store.commit('switchOnLoading');
      LandlordService.getLandlordDetails(this.id)
          .then(response => {
            this.landlord = response.data;
          })
          .catch(error => {
            this.landlord.id = null;
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
    editLandlord() {
      this.$router.push({name: 'landlordEdit', params: {id: this.landlord.id}});
    }
  },
  computed: {
    isLandlordFound() {
      return this.landlord.id != null;
    }
  },
  mounted() {
    this.loadLandlord();
  },
  watch: {
    id() {
      this.loadLandlord();
    }
  }
}
</script>

<style scoped>

</style>