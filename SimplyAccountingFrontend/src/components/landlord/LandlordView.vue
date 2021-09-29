<template>
  <v-card class="landlord-view">
    <template v-if="isLandlordFound">

      <v-card-title class="position-relative">
        <div class="mr-8">
          {{ landlord.fullName }}
          <edit-button
              class="mx-2"
              @click="editLandlord"/>
        </div>
        <v-menu
            left
            bottom>
          <template #activator="{ on, attrs }">
            <v-btn
                class="menu-button mr-2 mt-4 mr-sm-4"
                v-bind="attrs"
                v-on="on"
                :icon="isSmall"
                :outlined="!isSmall">
              <v-icon v-if="isSmall">
                mdi-dots-vertical
              </v-icon>
              <template v-if="!isSmall">
                {{ $_.capitalize($t('common.options')) }}
              </template>
            </v-btn>
          </template>
          <v-list>
            <v-list-item
                v-for="(option, index) in menuOptions"
                :key="index"
                @click="option.onClickFunction">
              <v-list-item-title>
                {{ option.text }}
              </v-list-item-title>
            </v-list-item>
          </v-list>
        </v-menu>
      </v-card-title>

      <v-card-subtitle class="py-0">
        {{ landlord.address }}
      </v-card-subtitle>

      <v-card-subtitle class="pt-0">
        {{ landlord.phoneNumber + ' ' + landlord.addressEmail }}
      </v-card-subtitle>

      <v-divider/>


    </template>

    <template v-else>
      <v-card-title class="error">
        {{ $t("landlord.messages.notFound") }}
      </v-card-title>
    </template>

  </v-card>
</template>

<script>
import LandlordService from "@/core/service/LandlordService";
import EditButton from "@/components/common/buttons/EditButton";

export default {
  name: "LandlordView",
  components: {EditButton},
  props: ['id'],
  data() {
    return {
      landlord: {
        id: null,
        fullName: null,
        address: null,
        phoneNumber: null,
        addressEmail: null,
        accountId: null
      }
    }
  },
  methods: {
    loadLandlord() {
      this.$loadingPromises([this.loadLandlordRequest()]);
    },
    loadLandlordRequest() {
      return LandlordService.getLandlordDetails(this.id)
          .then(response => {
            this.landlord = response.data;
          })
          .catch(error => {
            this.landlord.id = null;
            this.$errorToast(error);
          });
    },
    editLandlord() {
      this.$router.push({name: 'landlordEdit', params: {id: this.landlord.id}});
    },
    goToOpeningBalances() {
      this.$router.push({
        name: 'openingBalancesList',
        params: {accountId: this.landlord.accountId}
      });
    },
    goToLandlords() {
      this.$router.push({
        name: 'landlords'
      });
    }
  },
  computed: {
    isLandlordFound() {
      return this.landlord.id != null;
    },
    isSmall() {
      return this.$vssWidth <= 600;
    },
    menuOptions() {
      return [
        {
          text: this.$_.capitalize(this.$t('common.edit')),
          onClickFunction: this.editLandlord
        },
        {
          text: this.$_.capitalize(this.$t('openingBalance.openingBalances')),
          onClickFunction: this.goToOpeningBalances
        },
        {
          text: this.$_.capitalize(this.$t('landlord.landlords')),
          onClickFunction: this.goToLandlords
        },
      ];
    }
  },
  mounted() {
    this.loadLandlord();
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
