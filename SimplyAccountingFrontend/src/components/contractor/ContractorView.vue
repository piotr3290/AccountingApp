<template>
  <div>
    <div v-if="isContractorFound">
      <h2>{{ contractor.name }}</h2>
      <b-button @click="editContractor">{{ $_.capitalize($t('common.edit')) }}</b-button>
    </div>
    <div v-else>
      {{ $t("contractor.messages.notFound") }}
    </div>

  </div>
</template>

<script>
import ContractorService from "@/core/service/ContractorService";

export default {
  name: "ContractorView",
  props: ['id'],
  data() {
    return {
      contractor: {
        id: null,
        name: null,
        nip: null
      }
    }
  },
  methods: {
    loadContractor() {
      this.$store.commit('switchOnLoading');
      ContractorService.getContractor(this.id)
          .then(response => {
            this.contractor = response.data;
          })
          .catch(error => {
            this.contractor.id = null;
            this.$root.$bvToast.toast(error.response.data, {
              title: error.message,
              toaster: 'b-toaster-top-center',
              variant: 'danger'
            });
          })
          .finally(() => {
            this.$store.commit('switchOffLoading');
          })
    },
    editContractor() {
      this.$router.push({name: 'contractorEdit', params: {id: this.contractor.id}});
    }
  },
  computed: {
    isContractorFound() {
      return this.contractor.id != null;
    }
  },
  mounted() {
    this.loadContractor();
  },
  watch: {
    id() {
      this.loadContractor();
    }
  }
}
</script>

<style scoped>

</style>