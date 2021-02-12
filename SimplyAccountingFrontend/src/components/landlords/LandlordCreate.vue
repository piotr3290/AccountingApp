<template>
  <div>
    <div v-if="isFormVisible">
      <validation-observer ref="observer" v-slot="{handleSubmit}">

        <b-form @submit.prevent="handleSubmit(createEditLandlord)">


          <validation-provider
              :name="$t('common.firstName')"
              :rules="{required: true, max: 64}"
              v-slot="validationContext">
            <b-form-group
                id="input-group-first-name"
                :label="$_.capitalize($t('common.firstName'))"
                label-for="input-first-name">
              <b-form-input
                  id="input-first-name"
                  name="input-first-name"
                  v-model="landlord.firstName"
                  :state="getValidationState(validationContext)"
                  aria-describedby="input-first-name-feedback"
                  trim/>
              <b-form-invalid-feedback id="input-first-name-feedback">
                {{ validationContext.errors[0] }}
              </b-form-invalid-feedback>
            </b-form-group>
          </validation-provider>

          <validation-provider
              :name="$t('common.lastName')"
              :rules="{required: true, max: 64}"
              v-slot="validationContext">
            <b-form-group
                id="input-group-last-name"
                :label="$_.capitalize($t('common.lastName'))"
                label-for="input-last-name">
              <b-form-input
                  id="input-last-name"
                  name="input-last-name"
                  v-model="landlord.lastName"
                  :state="getValidationState(validationContext)"
                  aria-describedby="input-last-name-feedback"
                  trim/>
              <b-form-invalid-feedback id="input-last-name-feedback">
                {{ validationContext.errors[0] }}
              </b-form-invalid-feedback>
            </b-form-group>
          </validation-provider>

          <validation-provider
              :name="$t('common.emailAddress')"
              :rules="{max: 255}"
              v-slot="validationContext">
            <b-form-group
                id="input-group-email"
                :label="$_.capitalize($t('common.emailAddress'))"
                label-for="input-email">
              <b-form-input
                  id="input-email"
                  name="input-email"
                  v-model="landlord.emailAddress"
                  :state="getValidationState(validationContext)"
                  aria-describedby="input-email-feedback"
                  trim/>
              <b-form-invalid-feedback id="input-email-feedback">
                {{ validationContext.errors[0] }}
              </b-form-invalid-feedback>
            </b-form-group>
          </validation-provider>

          <validation-provider
              :name="$t('common.phoneNumber')"
              :rules="{max: 255}"
              v-slot="validationContext">
            <b-form-group
                id="input-group-phone"
                :label="$_.capitalize($t('common.phoneNumber'))"
                label-for="input-phone">
              <b-form-input
                  id="input-phone"
                  name="input-phone"
                  v-model="landlord.phoneNumber"
                  :state="getValidationState(validationContext)"
                  aria-describedby="input-phone-feedback"
                  trim/>
              <b-form-invalid-feedback id="input-phone-feedback">
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
                  v-model="landlord.street"
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
                  v-model="landlord.houseNumber"
                  :state="getValidationState(validationContext)"
                  aria-describedby="input-house-number-feedback"
                  trim/>
              <b-form-invalid-feedback id="input-house-number-feedback">
                {{ validationContext.errors[0] }}
              </b-form-invalid-feedback>
            </b-form-group>
          </validation-provider>

          <validation-provider
              :name="$t('common.premisesNumber')"
              :rules="{max: 8}"
              v-slot="validationContext">
            <b-form-group
                id="input-group-premises-number"
                :label="$_.capitalize($t('common.premisesNumber'))"
                label-for="input-premises-number">
              <b-form-input
                  id="input-premises-number"
                  name="input-premises-number"
                  v-model="landlord.premisesNumber"
                  :state="getValidationState(validationContext)"
                  aria-describedby="input-premises-number-feedback"
                  trim/>
              <b-form-invalid-feedback id="input-premises-number-feedback">
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
                  v-model="landlord.postalCode"
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
                  v-model="landlord.city"
                  :state="getValidationState(validationContext)"
                  aria-describedby="input-city-feedback"
                  trim/>
              <b-form-invalid-feedback id="input-city-feedback">
                {{ validationContext.errors[0] }}
              </b-form-invalid-feedback>
            </b-form-group>
          </validation-provider>

        </b-form>

      </validation-observer>

    </div>

  </div>
</template>

<script>
import LandlordService from "@/core/service/LandlordService";

export default {
  name: "LandlordCreate",
  props: ['id'],
  data() {
    return {
      landlord: {
        id: null,
        firstName: null,
        middleName: null,
        lastName: null,
        emailAddress: null,
        phoneNumber: null,
        street: null,
        houseNumber: null,
        premisesNumber: null,
        postalCode: null,
        city: null
      }
    }
  },
  methods: {
    getValidationState({dirty, validated, valid = null}) {
      return dirty || validated ? valid : null;
    },
    loadLandlord() {
      this.$store.commit('switchOnLoading');
      LandlordService.getLandlord(this.id)
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
    createEditLandlord() {
      this.$store.commit('switchOnLoading');

      let landlord = Object.assign({}, this.landlord);
      Object.keys(landlord)
          .forEach((function (key) {
            landlord[key] = landlord[key] || null
          }));

      if (this.id == null) {
        LandlordService.createLandlord(landlord)
            .then(() => {
              this.$root.$bvToast.toast(this.$t('landlords.messages.created'), {
                title: this.$_.capitalize(this.$t('common.success')),
                toaster: 'b-toaster-top-center',
                variant: 'success'
              });
              this.$router.push({name: 'landlords'});
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
        LandlordService.editLandlord(landlord)
            .then(response => {
              this.$root.$bvToast.toast(this.$t('landlords.messages.edited'), {
                title: this.$_.capitalize(this.$t('common.success')),
                toaster: 'b-toaster-top-center',
                variant: 'success'
              });
              this.$router.push({name: 'landlord', params: {id: response.data.id}});
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
    }
  },
  computed: {
    isFormVisible() {
      return this.id == null || this.landlord.id != null;
    }
  },
  mounted() {
    if (this.id != null) {
      this.loadLandlord();
    }
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