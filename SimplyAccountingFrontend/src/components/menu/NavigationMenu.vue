<template>
  <div class="navigation-menu">

    <v-navigation-drawer
        v-model="isDrawerVisible"
        app
        temporary>

      <v-list-item>
        <v-list-item-content class="justify-center">
          <v-list-item-title class="title flex-min">
            {{ $_.capitalize($t('common.menu')) }}
          </v-list-item-title>
        </v-list-item-content>
      </v-list-item>

      <v-divider></v-divider>

      <v-list nav>
        <v-list-item
            @click="goToDestination(item.name)"
            v-for="item in mainRoutes"
            :key="item.name">
          <v-list-item-icon>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-item-icon>
          <v-list-item-title>
            {{ item.displayText }}
          </v-list-item-title>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>

    <v-app-bar
        app>

      <v-app-bar-nav-icon
          v-if="$vssWidth <= 1000"
          @click="showDrawerMenu"/>

      <v-icon
          class="mr-sm-3 ml-sm-1"
          large
          @click="goHome">
        $appIcon
      </v-icon>

      <template v-if="$vssWidth > 680">
        <template v-for="(item, index) in mainRoutes">

          <v-divider
              v-if="index !== 0"
              :key="index"
              vertical
              inset/>

          <router-link-item
              :key="item.name"
              :route-name="item.name"
              :text="item.displayText"
              :icon="item.icon"/>

        </template>
      </template>

      <v-spacer/>

      <v-icon class="mx-2">
        mdi-theme-light-dark
      </v-icon>
      <v-switch
          :value="isDarkTheme"
          :input-value="isDarkTheme"
          @change="changeTheme"
          hide-details/>

      <language-select class="mx-2"/>
      <v-btn
          @click="logout"
          icon>
        <v-icon>
          mdi-logout
        </v-icon>
      </v-btn>

    </v-app-bar>
  </div>
</template>

<script>
import LanguageSelect from "@/components/common/form/LanguageSelect";
import RouterLinkItem from "@/components/menu/RouterLinkItem";

export default {
  name: "NavigationMenu",
  components: {RouterLinkItem, LanguageSelect},
  data() {
    return {
      isDrawerVisible: false
    }
  },
  computed: {
    isDarkTheme() {
      return this.$store.getters.isDarkTheme;
    },
    mainRoutes() {
      return [
        {
          name: 'cooperatives',
          displayText: this.$_.capitalize(this.$t('cooperative.cooperatives')),
          icon: 'mdi-home-city'
        },
        {
          name: 'accountsPlan',
          displayText: this.$_.capitalize(this.$t('account.plan')),
          icon: 'mdi-chart-tree'
        },
        {
          name: 'rates',
          displayText: this.$_.capitalize(this.$t('rate.rates')),
          icon: 'mdi-poll'
        },
        {
          name: 'landlords',
          displayText: this.$_.capitalize(this.$t('landlord.landlords')),
          icon: 'mdi-account-multiple'
        },
        {
          name: 'contractors',
          displayText: this.$_.capitalize(this.$t('contractor.contractors')),
          icon: 'mdi-account-hard-hat'
        },
        {
          name: 'moreOptions',
          displayText: this.$_.capitalize(this.$t('common.moreOptions')),
          icon: 'mdi-cog'
        }
      ]
    }
  },
  methods: {
    showDrawerMenu() {
      this.isDrawerVisible = true;
    },
    logout() {
      this.$store.commit('logOut');
      this.$router.push({name: 'login'});
    },
    goHome() {
      if (this.$route.name !== 'home') {
        this.$router.push({name: 'home'});
      }
    },
    changeTheme(value) {
      this.$vuetify.theme.dark = !this.$vuetify.theme.dark;
      this.$store.commit('changeTheme', value || false);
    },
    goToDestination(routeName) {
      if (this.$route.name !== routeName) {
        this.$router.push({name: routeName});
      }
    }
  }
}
</script>

<style scoped>

.language-select {
  max-width: 80px;
}

</style>