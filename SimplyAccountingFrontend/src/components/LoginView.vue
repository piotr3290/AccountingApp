<template>
  <div>
    <validation-observer ref="observer" v-slot="{handleSubmit}">

      <b-form @submit.prevent="handleSubmit(login)">

        <validation-provider
            :name="$t('common.username')"
            :rules="{required: true, max: 255}"
            v-slot="validationContext">
          <b-form-group
              id="input-group-username"
              :label="$_.capitalize($t('common.username'))"
              label-for="input-username">
            <b-form-input
                id="input-username"
                name="input-username"
                v-model="username"
                :state="getValidationState(validationContext)"
                aria-describedby="input-username-feedback"
                trim/>
            <b-form-invalid-feedback id="input-username-feedback">
              {{ validationContext.errors[0] }}
            </b-form-invalid-feedback>
          </b-form-group>
        </validation-provider>

        <validation-provider
            :name="$t('common.password')"
            :rules="{required: true, max: 255}"
            v-slot="validationContext">
          <b-form-group
              id="input-group-password"
              :label="$_.capitalize($t('common.password'))"
              label-for="input-password">
            <b-form-input
                id="input-password"
                name="input-password"
                v-model="password"
                :state="getValidationState(validationContext)"
                aria-describedby="input-password-feedback"
                type="password"
                trim/>
            <b-form-invalid-feedback id="input-password-feedback">
              {{ validationContext.errors[0] }}
            </b-form-invalid-feedback>
          </b-form-group>
        </validation-provider>
        <b-button type="submit">{{ $_.capitalize($t('common.signIn')) }}</b-button>
      </b-form>

    </validation-observer>
  </div>
</template>

<script>
import AuthenticationService from "@/core/service/AuthenticationService";

export default {
  name: "LoginView",
  data() {
    return {
      username: null,
      password: null
    }
  },
  methods: {
    login() {
      this.$store.commit('switchOnLoading');
      AuthenticationService.signIn(this.username, this.password)
          .then(response => {
            this.$store.commit('authenticate', response.data.token);
            this.$router.push({name: 'home'});
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
    getValidationState({dirty, validated, valid = null}) {
      return dirty || validated ? valid : null;
    }
  }
}
</script>

<style scoped>

</style>