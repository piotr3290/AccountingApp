<template>
  <v-card class="contractor-view">

    <template v-if="isContractorFound">

      <v-card-title class="position-relative">
        <div class="mr-8">
          {{ contractor.name }}
          <edit-button
              class="mx-2"
              @click="editContractor"/>
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

      <v-card-subtitle>
        {{ $t('common.nip') + ': ' + contractor.nip }}
      </v-card-subtitle>

    </template>

    <template v-else>
      <v-card-title class="error">
        {{ $t("contractor.messages.notFound") }}
      </v-card-title>
    </template>

  </v-card>
</template>

<script>
import ContractorService from "@/core/service/ContractorService";
import EditButton from "@/components/common/buttons/EditButton";

export default {
  name: "ContractorView",
  components: {EditButton},
  props: ['id'],
  data() {
    return {
      contractor: {
        id: null,
        name: null,
        nip: null,
        accountId: null
      }
    }
  },
  methods: {
    loadContractor() {
      this.$loadingPromises([this.loadContractorRequest()]);
    },
    loadContractorRequest() {
      return ContractorService.getContractor(this.id)
          .then(response => {
            this.contractor = response.data;
          })
          .catch(error => {
            this.contractor.id = null;
            this.$errorToast(error);
          });
    },
    editContractor() {
      this.$router.push({
        name: 'contractorEdit',
        params: {id: this.contractor.id}
      });
    },
    goToOpeningBalances() {
      this.$router.push({
        name: 'openingBalancesList',
        params: {accountId: this.contractor.accountId}
      });
    },
    goToContractors() {
      this.$router.push({
        name: 'contractors'
      });
    }
  },
  computed: {
    isContractorFound() {
      return this.contractor.id != null;
    },
    isSmall() {
      return this.$vssWidth <= 600;
    },
    menuOptions() {
      return [
        {
          text: this.$_.capitalize(this.$t('common.edit')),
          onClickFunction: this.editContractor
        },
        {
          text: this.$_.capitalize(this.$t('openingBalance.openingBalances')),
          onClickFunction: this.goToOpeningBalances
        },
        {
          text: this.$_.capitalize(this.$t('contractor.contractors')),
          onClickFunction: this.goToContractors
        },
      ];
    }
  },
  mounted() {
    this.loadContractor();
  },
  watch: {
    id() {
      this.loadContractor();
    }
  }
}
</script>

<style scoped>

</style>
