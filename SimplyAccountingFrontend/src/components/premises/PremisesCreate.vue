<template>
  <div>
    <div v-if="isFormVisible">
      <h2>{{ premises.buildingAddress }}</h2>
      <validation-observer ref="observer" v-slot="{handleSubmit}">
        <b-form @submit.prevent="handleSubmit(createEditPremises)">

          <validation-provider
              :name="$t('common.premisesNumber')"
              :rules="{required: true, max: 8}"
              v-slot="validationContext">
            <b-form-group
                id="input-group-premises-number"
                :label="$_.capitalize($t('common.premisesNumber'))"
                label-for="input-premises-number">
              <b-form-input
                  id="input-premises-number"
                  name="input-premises-number"
                  v-model="premises.premisesNumber"
                  :state="getValidationState(validationContext)"
                  aria-describedby="input-street-premises-number"
                  trim/>
              <b-form-invalid-feedback id="input-premises-number-feedback">
                {{ validationContext.errors[0] }}
              </b-form-invalid-feedback>
            </b-form-group>
          </validation-provider>

          <validation-provider
              :name="$t('premises.type')"
              :rules="{required: true}"
              v-slot="validationContext">
            <b-form-group
                id="input-group-premises-type"
                :label="$_.capitalize($t('premises.type'))"
                label-for="input-premises-type">
              <b-form-select
                  id="input-premises-type"
                  name="input-premises-type"
                  v-model="premises.premisesTypeId"
                  :options="premisesTypes"
                  text-field="name"
                  value-field="id"
                  :state="getValidationState(validationContext)"
                  aria-describedby="input-premises-type-feedback"
                  trim>
                <template #first>
                  <b-form-select-option :value="null" disabled>{{ $t('premises.messages.chooseType') }}
                  </b-form-select-option>
                </template>
              </b-form-select>
              <b-form-invalid-feedback id="input-premises-type-feedback">
                {{ validationContext.errors[0] }}
              </b-form-invalid-feedback>
            </b-form-group>
          </validation-provider>

          <b-button type="submit">{{ createOrEdit }}</b-button>
          <b-button @click="cancelForm">{{ $_.capitalize($t('common.cancel')) }}</b-button>

        </b-form>
      </validation-observer>
    </div>
    <div v-else-if="!isBuildingFound && buildingId != null">
      <h2>{{ $t('building.messages.notFound') }}</h2>
    </div>
    <div v-else>
      <h2>{{ $t('premises.messages.notFound') }}</h2>
    </div>
  </div>
</template>

<script>
import BuildingService from "@/core/service/BuildingService";
import PremisesService from "@/core/service/PremisesService";

export default {
  name: "PremisesCreate",
  props: ['id', 'buildingId'],
  data() {
    return {
      premises: {
        id: null,
        premisesNumber: null,
        premisesTypeId: null,
        buildingId: null,
        buildingAddress: null
      },
      premisesTypes: []
    }
  },
  methods: {
    getValidationState({dirty, validated, valid = null}) {
      return dirty || validated ? valid : null;
    },
    loadBuilding() {
      this.$store.commit('switchOnLoading');
      BuildingService.getBuilding(this.buildingId)
          .then(response => {
            let building = response.data;
            this.premises.buildingAddress = building.street + ' ' + building.houseNumber + ' ' + building.postalCode;
            this.premises.buildingId = building.id;
          })
          .catch(error => {
            this.premises.buildingId = null;
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
    loadPremises() {
      this.$store.commit('switchOnLoading');
      PremisesService.getPremises(this.id)
          .then(response => {
            this.premises = response.data;
          })
          .catch(error => {
            this.premises.id = null;
            this.premises.buildingId = null;
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
    loadAllPremisesTypes() {
      this.$store.commit('switchOnLoading');
      PremisesService.getAllPremisesTypes()
          .then(response => {
            this.premisesTypes = response.data;
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
    createEditPremises() {
      this.$store.commit('switchOnLoading');
      if (this.premises.id == null) {


        PremisesService.createPremises(this.premises)
            .then(() => {
              this.$root.$bvToast.toast(this.$t('premises.messages.created'), {
                title: this.$_.capitalize(this.$t('common.success')),
                toaster: 'b-toaster-top-center',
                variant: 'success'
              });
              this.$router.push({name: 'building', params: {id: this.buildingId}});
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
        PremisesService.editPremises(this.premises)
            .then(() => {
              this.$root.$bvToast.toast(this.$t('premises.messages.edited'), {
                title: this.$_.capitalize(this.$t('common.success')),
                toaster: 'b-toaster-top-center',
                variant: 'success'
              });
              this.$router.push({name: 'premises', params: {id: this.id}});
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
      if (this.premises.id != null) {
        this.$router.push({name: 'premises', params: {id: this.premises.id}});
      } else if (this.premises.buildingId != null) {
        this.$router.push({name: 'building', params: {id: this.premises.buildingId}});
      } else {
        this.$router.back();
      }
    }
  },
  computed: {
    createOrEdit() {
      return this.id != null ? this.$_.capitalize(this.$t('common.edit')) : this.$_.capitalize(this.$t('common.create'));
    },
    isBuildingFound() {
      return this.premises.buildingId != null;
    },
    isPremisesFound() {
      return this.premises.id;
    },
    isFormVisible() {
      return this.isBuildingFound || this.isPremisesFound;
    }
  },
  mounted() {
    this.loadAllPremisesTypes();
    if (this.id != null) {
      this.loadPremises();
    }
    if (this.buildingId != null) {
      this.loadBuilding();
    }
  },
  watch: {
    id() {
      this.loadPremises();
    },
    buildingId() {
      this.loadBuilding();
    }
  }
}
</script>

<style scoped>

</style>