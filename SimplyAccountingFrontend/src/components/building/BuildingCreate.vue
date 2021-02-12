<template>
  <div>
    <div v-if="isFormVisible">

      <h2>{{ building.cooperativeName }}</h2>

      <validation-observer ref="observer" v-slot="{handleSubmit}">
        <b-form @submit.prevent="handleSubmit(createEditBuilding)">
          <validation-provider
              :name="$t('common.street')"
              :rules="{required: true, max: 255}"
              v-slot="validationContext">
            <b-form-group
                id="input-group-street"
                :label="$_.capitalize($t('common.street'))"
                label-for="input-street">
              <b-form-input
                  id="input-street"
                  name="input-street"
                  v-model="building.street"
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
              :rules="{required: true, max: 8}"
              v-slot="validationContext">
            <b-form-group
                id="input-group-house-number"
                :label="$_.capitalize($t('common.houseNumber'))"
                label-for="input-house-number">
              <b-form-input
                  id="input-house-number"
                  name="input-house-number"
                  v-model="building.houseNumber"
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
              :rules="{required: true, regex: /^[0-9]{2}-[0-9]{3}$/}"
              v-slot="validationContext">
            <b-form-group
                id="input-group-postal-code"
                :label="$_.capitalize($t('common.postalCode'))"
                label-for="input-postal-code">
              <b-form-input
                  id="input-postal-code"
                  name="input-postal-code"
                  v-model="building.postalCode"
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
              :rules="{required: true, max: 64}"
              v-slot="validationContext">
            <b-form-group
                id="input-group-city"
                :label="$_.capitalize($t('common.city'))"
                label-for="input-city">
              <b-form-input
                  id="input-city"
                  name="input-city"
                  v-model="building.city"
                  :state="getValidationState(validationContext)"
                  aria-describedby="input-city-feedback"
                  trim/>
              <b-form-invalid-feedback id="input-city-feedback">
                {{ validationContext.errors[0] }}
              </b-form-invalid-feedback>
            </b-form-group>
          </validation-provider>
          <b-button type="submit">{{ createOrEdit }}</b-button>
          <b-button @click="$router.back()">{{ $_.capitalize($t('common.cancel')) }}</b-button>

        </b-form>
      </validation-observer>
    </div>
    <div v-else-if="!isCooperativeFound && cooperativeId != null">
      <h2>{{ $t('cooperative.messages.notFound') }}</h2>
    </div>
    <div v-else>
      <h2>{{ $t('building.messages.notFound') }}</h2>
    </div>
  </div>
</template>

<script>
import BuildingService from "@/core/service/BuildingService";
import CooperativeService from "@/core/service/CooperativeService";

export default {
  name: "BuildingCreate",
  props: ['id', 'cooperativeId'],
  data() {
    return {
      building: {
        id: null,
        street: null,
        houseNumber: null,
        postalCode: null,
        city: null,
        cooperativeId: null,
        cooperativeName: null
      }
    }
  },
  methods: {
    createEditBuilding() {
      this.$store.commit('switchOnLoading');
      if (this.id == null) {
        BuildingService.createBuilding(this.building)
            .then(() => {
              this.$root.$bvToast.toast(this.$t('building.messages.created'), {
                title: this.$_.capitalize(this.$t('common.success')),
                toaster: 'b-toaster-top-center',
                variant: 'success'
              });
              this.$router.push({name: 'cooperative', params: {id: this.cooperativeId}});
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
        BuildingService.editBuilding(this.building)
            .then(() => {
              this.$root.$bvToast.toast(this.$t('building.messages.edited'), {
                title: this.$_.capitalize(this.$t('common.success')),
                toaster: 'b-toaster-top-center',
                variant: 'success'
              });
              this.$router.push({name: 'building', params: {id: this.id}});
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
    getValidationState({dirty, validated, valid = null}) {
      return dirty || validated ? valid : null;
    },
    loadCooperative() {
      this.$store.commit('switchOnLoading');
      CooperativeService.getCooperative(this.cooperativeId)
          .then(response => {
            this.building.cooperativeId = response.data.id;
            this.building.cooperativeName = response.data.name;
          })
          .catch(error => {
            this.$root.$bvToast.toast(error.response.data, {
              title: error.message,
              toaster: 'b-toaster-top-center',
              variant: 'danger'
            });
            this.building.cooperativeId = null;
            this.building.cooperativeName = null;
          })
          .finally(() => {
            this.$store.commit('switchOffLoading');
          });

    },
    loadBuilding() {
      this.$store.commit('switchOnLoading');
      BuildingService.getBuilding(this.id)
          .then(response => {
            this.building = response.data;
          })
          .catch(error => {
            this.building.id = null;
            this.building.cooperativeId = null;
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
  computed: {
    createOrEdit() {
      return this.id != null ? this.$_.capitalize(this.$t('common.edit')) : this.$_.capitalize(this.$t('common.create'));
    },
    isFormVisible() {
      return this.isBuildingFound || this.isCooperativeFound;
    },
    isBuildingFound() {
      return this.building.id != null;
    },
    isCooperativeFound() {
      return this.building.cooperativeId != null;
    }
  },
  mounted() {
    if (this.cooperativeId != null) {
      this.loadCooperative();
    }
    if (this.id != null) {
      this.loadBuilding();
    }

  },
  watch: {
    cooperativeId() {
      this.loadCooperative();
    },
    id() {
      this.loadBuilding();
    }
  }
}
</script>

<style scoped>

</style>