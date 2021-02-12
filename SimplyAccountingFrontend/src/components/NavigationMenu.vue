<template>
  <div id="navigation-menu">
    <h1>MENU</h1>
    <b-navbar>
      <b-navbar-nav>
        <b-nav-item>
          <router-link to="/cooperatives">{{ $_.capitalize($t('cooperative.cooperatives')) }}</router-link>
        </b-nav-item>
        <b-nav-item>
          <router-link to="/landlords">{{ $_.capitalize($t('landlords.landlords')) }}</router-link>
        </b-nav-item>
      </b-navbar-nav>
      <b-navbar-nav class="ml-auto">
        <b-button @click="logout">{{ $_.capitalize($t('common.logout')) }}</b-button>
      </b-navbar-nav>
    </b-navbar>
  </div>
</template>

<script>
export default {
  name: "NavigationMenu",
  methods: {
    logout() {
      this.$store.commit('logOut');
      this.$router.push({name: 'login'});
    }
  }
}
</script>

<style scoped>

</style>