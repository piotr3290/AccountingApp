<template>
  <v-app>
    <navigation-menu v-if="isAuthenticated"/>
    <v-main class="mt-4">
      <loading v-if="showLoading"/>
      <v-container>
        <router-view/>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import NavigationMenu from "@/components/menu/NavigationMenu";
import HttpRequestService from "@/core/service/HttpRequestService";
import Loading from "@/components/common/Loading";

export const httpRequestService = new HttpRequestService('http://10.12.42.229:9090');

export default {
  name: 'App',
  components: {
    Loading,
    NavigationMenu
  },
  computed: {
    showLoading() {
      return this.$store.getters.getLoadingState;
    },
    isAuthenticated() {
      return this.$store.getters.isAuthenticated;
    }
  }

}
</script>

<style>

</style>
