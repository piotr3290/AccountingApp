<template>
  <v-row justify="center">
    <v-col md="6" class="mt-10 mx-sm-6">
      <v-card>

        <v-card-title class="primary">
          {{ $_.capitalize($t('common.login')) }}
        </v-card-title>
        
        <validation-observer ref="observer" v-slot="{handleSubmit}">

          <form @submit.prevent="handleSubmit(login)">

            <v-card-text>

              <validation-provider
                  :name="$t('common.username')"
                  :rules="{required: true, max: 255}"
                  v-slot="validationContext">

                <text-input
                    class="mt-6"
                    v-model="username"
                    tag-name="username"
                    :errors="validationContext.errors"
                    :label="$_.capitalize($t('common.username'))"/>
              </validation-provider>

              <validation-provider
                  :name="$t('common.password')"
                  :rules="{required: true, max: 255}"
                  v-slot="validationContext">

                <text-input
                    class="mt-6"
                    v-model="password"
                    tag-name="password"
                    :errors="validationContext.errors"
                    :label="$_.capitalize($t('common.password'))"
                    type="password"/>
              </validation-provider>

            </v-card-text>

            <v-card-actions class="justify-end">
              <default-button
                  class="ma-2"
                  type="submit"
                  :text="$_.capitalize($t('common.signIn'))"/>
            </v-card-actions>

          </form>
        </validation-observer>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
import AuthenticationService from "@/core/service/AuthenticationService";
import TextInput from "@/components/common/form/TextInput";
import DefaultButton from "@/components/common/buttons/DefaultButton";

export default {
  name: "LoginView",
  components: {DefaultButton, TextInput},
  data() {
    return {
      username: null,
      password: null
    }
  },
  methods: {
    login() {
      this.$loadingPromises([this.loginRequest()]);
    },
    loginRequest() {
      return AuthenticationService.signIn(this.username, this.password)
          .then(response => {
            this.$store.commit('authenticate', response.data.token);
            this.$router.push({name: 'home'});
          })
          .catch(error => {
            this.$errorToast(error);
          });
    }
  }
}
</script>

<style scoped>

</style>