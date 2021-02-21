<template>
  <div>
    <div v-if="isFormVisible">
      <validation-observer ref="observer" v-slot="{handleSubmit}">
        <b-form @submit.prevent="handleSubmit(createEditCooperative)">

          <validation-provider
              :name="$t('cooperative.name')"
              :rules="{required: true, min: 3, max: 255}"
              v-slot="validationContext">
            <b-form-group
                id="input-group-name"
                :label="$_.capitalize($t('cooperative.name'))"
                label-for="input-name">
              <b-form-input
                  id="input-name"
                  name="input-name"
                  v-model="cooperative.name"
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
                  v-model="cooperative.nip"
                  :state="getValidationState(validationContext)"
                  aria-describedby="input-nip-feedback"
                  trim/>
              <b-form-invalid-feedback id="input-nip-feedback">
                {{ validationContext.errors[0] }}
              </b-form-invalid-feedback>
            </b-form-group>
          </validation-provider>

          <validation-provider
              :name="$t('cooperative.regon')"
              :rules="{regex: /^[0-9]+$/, max: 14}"
              v-slot="validationContext">
            <b-form-group
                id="input-group-regon"
                :label="$t('cooperative.regon')"
                label-for="input-regon">
              <b-form-input
                  id="input-regon"
                  name="input-regon"
                  v-model="cooperative.regon"
                  :state="getValidationState(validationContext)"
                  aria-describedby="input-regon-feedback"
                  trim/>
              <b-form-invalid-feedback id="input-regon-feedback">
                {{ validationContext.errors[0] }}
              </b-form-invalid-feedback>
            </b-form-group>
          </validation-provider>

          <validation-provider
              :name="$t('common.street')"
              :rules="{max: 255}"
              v-slot="validationContext">
            <b-form-group
                id="input-group-street"
                :label="$_.capitalize($t('common.street'))"
                label-for="input-street">
              <b-form-input
                  id="input-street"
                  name="input-street"
                  v-model="cooperative.street"
                  :state="getValidationState(validationContext)"
                  aria-describedby="input-street-feedback"
                  trim/>
              <b-form-invalid-feedback id="input-street-feedback">
                {{ validationContext.errors[0] }}
              </b-form-invalid-feedback>
            </b-form-group>
          </validation-provider>

          <validation-provider
              :name="$t('common.houseNumber')"
              :rules="{max: 8}"
              v-slot="validationContext">
            <b-form-group
                id="input-group-house-number"
                :label="$_.capitalize($t('common.houseNumber'))"
                label-for="input-house-number">
              <b-form-input
                  id="input-house-number"
                  name="input-house-number"
                  v-model="cooperative.houseNumber"
                  :state="getValidationState(validationContext)"
                  aria-describedby="input-house-number-feedback"
                  trim/>
              <b-form-invalid-feedback id="input-house-number-feedback">
                {{ validationContext.errors[0] }}
              </b-form-invalid-feedback>
            </b-form-group>
          </validation-provider>

          <validation-provider
              :name="$t('common.postalCode')"
              :rules="{regex: /^[0-9]{2}-[0-9]{3}$/}"
              v-slot="validationContext">
            <b-form-group
                id="input-group-postal-code"
                :label="$_.capitalize($t('common.postalCode'))"
                label-for="input-postal-code">
              <b-form-input
                  id="input-postal-code"
                  name="input-postal-code"
                  v-model="cooperative.postalCode"
                  :state="getValidationState(validationContext)"
                  aria-describedby="input-postal-code-feedback"
                  trim/>
              <b-form-invalid-feedback id="input-postal-code-feedback">
                {{ validationContext.errors[0] }}
              </b-form-invalid-feedback>
            </b-form-group>
          </validation-provider>

          <validation-provider
              :name="$t('common.city')"
              :rules="{max: 64}"
              v-slot="validationContext">
            <b-form-group
                id="input-group-city"
                :label="$_.capitalize($t('common.city'))"
                label-for="input-city">
              <b-form-input
                  id="input-city"
                  name="input-city"
                  v-model="cooperative.city"
                  :state="getValidationState(validationContext)"
                  aria-describedby="input-city-feedback"
                  trim/>
              <b-form-invalid-feedback id="input-city-feedback">
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
      {{ $t('cooperative.messages.notFound') }}
    </div>
  </div>
</template>

<script>
import CooperativeService from "@/core/service/CooperativeService";

export default {
  name: "CooperativeCreate",
  props: ['id'],
  data() {
    return {
      cooperative: {
        id: null,
        name: null,
        nip: null,
        regon: null,
        city: null,
        street: null,
        houseNumber: null,
        postalCode: null
      }
    }
  },
  mounted() {
    if (this.id != null) {
      this.loadCooperative();
    }
  },
  computed: {
    createOrEdit() {
      return this.id != null ? this.$_.capitalize(this.$t('common.edit')) : this.$_.capitalize(this.$t('common.create'));
    },
    isFormVisible() {
      return this.id == null || this.cooperative.id != null;
    }
  },
  methods: {
    createEditCooperative() {
      this.$store.commit('switchOnLoading');

      let cooperative = Object.assign({}, this.cooperative);
      Object.keys(cooperative)
          .forEach((function (key) {
            cooperative[key] = cooperative[key] || null
          }));

      if (this.id == null) {
        CooperativeService.createCooperative(cooperative)
            .then(() => {
              this.$root.$bvToast.toast(this.$t('cooperative.messages.created'), {
                title: this.$_.capitalize(this.$t('common.success')),
                toaster: 'b-toaster-top-center',
                variant: 'success'
              });
              this.$router.push({name: 'cooperatives'});
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
        CooperativeService.editCooperative(cooperative)
            .then(response => {
              this.$root.$bvToast.toast(this.$t('cooperative.messages.edited'), {
                title: this.$_.capitalize(this.$t('common.success')),
                toaster: 'b-toaster-top-center',
                variant: 'success'
              });
              this.$router.push({name: 'cooperative', params: {id: response.data.id}});
            })
            .catch(error => {
              this.$bvToast.toast(error.response.data, {
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
    getValidationState({dirty, validated, valid = null}) {
      return dirty || validated ? valid : null;
    },
    loadCooperative() {
      this.$store.commit('switchOnLoading');
      CooperativeService.getCooperative(this.id)
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
    cancelForm() {
      if (this.cooperative.id != null) {
        this.$router.push({name: 'cooperative', params: {id: this.cooperative.id}});
      } else {
        this.$router.push({name: 'cooperatives'});
      }
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