<template>
  <div>
    <validation-observer ref="observer" v-slot="{handleSubmit}">
      <b-form @submit.prevent="handleSubmit(createEditArea)">

        <validation-provider
            :name="$t('area.surface')"
            :rules="{required: true, min: 0}"
            v-slot="validationContext">
          <b-form-group
              id="input-group-value"
              :label="$_.capitalize($t('area.surface'))"
              label-for="input-value">
            <b-form-input
                id="input-value"
                name="input-value"
                v-model="area.value"
                type="number"
                :state="getValidationState(validationContext)"
                aria-describedby="input-value-feedback"
                trim/>
            <b-form-invalid-feedback id="input-value-feedback">
              {{ validationContext.errors[0] }}
            </b-form-invalid-feedback>
          </b-form-group>
        </validation-provider>


        <validation-provider
            :name="$t('common.from')"
            :rules="{required: true}"
            v-slot="validationContext">
          <b-form-group
              id="input-group-start-date"
              :label="$_.capitalize($t('common.from'))"
              label-for="input-start-date">
            <b-form-input
                id="input-start-date"
                name="input-start-date"
                v-model="area.startDate"
                type="date"
                :state="getValidationState(validationContext)"
                aria-describedby="input-start-date-feedback"/>
            <b-form-invalid-feedback id="input-start-date-feedback">
              {{ validationContext.errors[0] }}
            </b-form-invalid-feedback>
          </b-form-group>
        </validation-provider>

        <validation-provider
            :name="$t('common.to')"
            :rules="{required: true}"
            v-slot="validationContext">
          <b-form-group
              id="input-group-end-date"
              :label="$_.capitalize($t('common.to'))"
              label-for="input-end-date">
            <b-form-input
                id="input-end-date"
                name="input-end-date"
                v-model="area.endDate"
                type="date"
                :state="getValidationState(validationContext)"
                aria-describedby="input-end-date-feedback"/>
            <b-form-invalid-feedback id="input-end-date-feedback">
              {{ validationContext.errors[0] }}
            </b-form-invalid-feedback>
          </b-form-group>
        </validation-provider>

        <b-button type="submit">{{ createOrEdit }}</b-button>
        <b-button @click="cancelForm">{{ $_.capitalize($t('common.cancel')) }}</b-button>

      </b-form>
    </validation-observer>

  </div>
</template>

<script>
import AreaService from "@/core/service/AreaService";

export default {
  name: "AreaCreate",
  props: ['id', 'premisesId'],
  data() {
    return {
      area: {
        id: null,
        startDate: null,
        endDate: null,
        value: null,
        premisesId: null
      }
    }
  },
  methods: {
    cancelForm() {
      this.$emit('cancel');
    },
    createEditArea() {
      this.$store.commit('switchOnLoading');

      let area = Object.assign({}, this.area);
      Object.keys(area)
          .forEach((function (key) {
            area[key] = area[key] || null
          }));

      if (this.area.id == null) {
        AreaService.createArea(this.area)
            .then(response => {
              this.$root.$bvToast.toast(this.$t('area.messages.created'), {
                title: this.$_.capitalize(this.$t('common.success')),
                toaster: 'b-toaster-top-center',
                variant: 'success'
              });
              this.$emit('updateAreas', response.data);
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
        AreaService.editArea(this.area)
            .then(response => {
              this.$root.$bvToast.toast(this.$t('area.messages.edited'), {
                title: this.$_.capitalize(this.$t('common.success')),
                toaster: 'b-toaster-top-center',
                variant: 'success'
              });
              this.$emit('updateAreas', response.data);
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
    loadArea() {
      this.$store.commit('switchOnLoading');
      AreaService.getArea(this.id)
          .then(response => {
            this.area = response.data;
          })
          .catch(error => {
            this.area.id = null;
            this.$root.$bvToast.toast(error.response.data, {
              title: error.message,
              toaster: 'b-toaster-top-center',
              variant: 'danger'
            });
          })
          .finally(() => {
            this.$store.commit('switchOffLoading');
          })
    }
  },
  computed: {
    createOrEdit() {
      return this.id != null ? this.$_.capitalize(this.$t('common.edit')) : this.$_.capitalize(this.$t('common.create'));
    }
  },
  mounted() {
    if (this.id != null) {
      this.loadArea();
    } else {
      this.area.premisesId = this.premisesId;
    }
  }
}
</script>

<style scoped>

</style>