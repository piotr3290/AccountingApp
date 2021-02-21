<template>
  <div>
    <div v-if="isFormVisible">
      <validation-observer ref="observer" v-slot="{handleSubmit}">
        <b-form @submit.prevent="handleSubmit(createEditContractor)">

          <validation-provider
              :name="$t('contractor.name')"
              :rules="{required: true, min: 3, max: 64}"
              v-slot="validationContext">
            <b-form-group
                id="input-group-name"
                :label="$_.capitalize($t('contractor.name'))"
                label-for="input-name">
              <b-form-input
                  id="input-name"
                  name="input-name"
                  v-model="contractor.name"
                  :state="getValidationState(validationContext)"
                  aria-describedby="input-name-feedback"
                  trim/>
              <b-form-invalid-feedback id="input-name-feedback">
                {{ validationContext.errors[0] }}
              </b-form-invalid-feedback>
            </b-form-group>
          </validation-provider>

          <validation-provider
              :name="$t('common.nip')"
              :rules="{regex: /^[0-9]+$/, max: 10}"
              v-slot="validationContext">
            <b-form-group
                id="input-group-nip"
                :label="$t('common.nip')"
                label-for="input-nip">
              <b-form-input
                  id="input-nip"
                  name="input-nip"
                  v-model="contractor.nip"
                  :state="getValidationState(validationContext)"
                  aria-describedby="input-nip-feedback"
                  trim/>
              <b-form-invalid-feedback id="input-nip-feedback">
                {{ validationContext.errors[0] }}
              </b-form-invalid-feedback>
            </b-form-group>
          </validation-provider>

          <b-button type="submit">{{ createOrEdit }}</b-button>
          <b-button @click="cancelForm">{{ $_.capitalize($t('common.cancel')) }}</b-button>

        </b-form>
      </validation-observer>
    </div>
    <div v-else>
      {{ $t('contractor.messages.notFound') }}
    </div>

  </div>
</template>

<script>
import ContractorService from "@/core/service/ContractorService";

export default {
  name: "ContractorCreate",
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
    getValidationState({dirty, validated, valid = null}) {
      return dirty || validated ? valid : null;
    },
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
          });
    },
    createEditContractor() {
      this.$store.commit('switchOnLoading');

      let contractor = Object.assign({}, this.contractor);
      Object.keys(contractor)
          .forEach((function (key) {
            contractor[key] = contractor[key] || null
          }));

      if (this.contractor.id == null) {
        ContractorService.createContractor(contractor)
            .then(() => {
              this.$root.$bvToast.toast(this.$t('contractor.messages.created'), {
                title: this.$_.capitalize(this.$t('common.success')),
                toaster: 'b-toaster-top-center',
                variant: 'success'
              });
              this.$router.push({name: 'contractors'});
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
      } else {
        ContractorService.editContractor(contractor)
            .then(response => {
              this.$root.$bvToast.toast(this.$t('contractor.messages.edited'), {
                title: this.$_.capitalize(this.$t('common.success')),
                toaster: 'b-toaster-top-center',
                variant: 'success'
              });
              this.$router.push({name: 'contractor', params: {id: response.data.id}});
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
      }
    },
    cancelForm() {
      if (this.contractor.id != null) {
        this.$router.push({name: 'contractor', params: {id: this.contractor.id}});
      } else {
        this.$router.push({name: 'contractors'});
      }
    }
  },
  computed: {
    isFormVisible() {
      return this.id == null || this.contractor.id != null;
    },
    createOrEdit() {
      return this.id != null ? this.$_.capitalize(this.$t('common.edit')) : this.$_.capitalize(this.$t('common.create'));
    },
  },
  mounted() {
    if (this.id != null) {
      this.loadContractor();
    }
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