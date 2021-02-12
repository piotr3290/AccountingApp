<template>
  <div id="app">
    <loading v-if="showLoading"/>
    <navigation-menu v-if="$store.getters.isAuthenticated"/>
    <router-view/>
  </div>
</template>

<script>
import NavigationMenu from "@/components/NavigationMenu";
import HttpRequestService from "@/core/service/HttpRequestService";
import Loading from "@/components/Loading";

export const httpRequestService = new HttpRequestService('http://localhost:9090');

export default {
  name: 'App',
  components: {
    Loading,
    NavigationMenu
  },
  computed: {
    showLoading() {
      return this.$store.getters.getLoadingState;
    }
  },
  created() {
    this.$store.commit('loadJwtFromStorage');
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
