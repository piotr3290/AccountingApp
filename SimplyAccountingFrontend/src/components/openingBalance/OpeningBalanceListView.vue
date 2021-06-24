<template>
  <div class="opening-balance-list-view">
    <v-data-table
        class="elevation-1"
        :headers="tableHeaders"
        :items="openingBalances"
        :no-data-text="$t('openingBalance.messages.noBalance')"
        multi-sort
        hide-default-footer>

      <template #top>
        <v-card-title class="text-h4">
          {{ $_.capitalize($t('openingBalance.openingBalances')) }}
          <add-button
              class="mx-4"
              @click="showForm"/>
          <v-spacer/>
          <default-button
              :text="account.subjectName"
              @click="goBack"/>
        </v-card-title>
        <v-divider/>
      </template>

      <template #item.balance="{ item }">
        {{ currency(item.balance) }}
      </template>

      <template #item.actions="{ item }">
        <edit-button
            :disabled="isFormVisible"
            @click="showEditForm(item.id)"/>
      </template>

      <template #footer>

        <v-divider/>

        <div class="text-center">
          <add-button
              :disabled="isFormVisible"
              class="my-2"
              @click="showForm"/>
        </div>

      </template>

    </v-data-table>

    <v-dialog
        v-model="isFormVisible"
        max-width="400px">
      <opening-balance-form
          :account-id="accountId"
          :id="editingOpeningBalanceId"
          @updateOpeningBalances="updateList($event)"
          @cancel="hideForm"/>
    </v-dialog>

  </div>
</template>

<script>
import OpeningBalanceService from "@/core/service/OpeningBalanceService";
import {currency} from "@/core/service/FormatterService";
import OpeningBalanceForm from "@/components/openingBalance/OpeningBalanceForm";
import EditButton from "@/components/common/buttons/EditButton";
import AddButton from "@/components/common/buttons/AddButton";
import AccountService from "@/core/service/AccountService";
import DefaultButton from "@/components/common/buttons/DefaultButton";

export default {
  name: "OpeningBalanceListView",
  components: {DefaultButton, AddButton, EditButton, OpeningBalanceForm},
  props: ['accountId'],
  data() {
    return {
      openingBalances: [],
      isFormVisible: false,
      editingOpeningBalanceId: null,
      account: {
        id: null,
        accountTypeId: null,
        subjectName: null,
        subjectId: null
      }
    }
  },
  computed: {
    tableHeaders() {
      return [
        {
          text: this.$_.capitalize(this.$t('common.year')),
          value: 'year'
        },
        {
          text: this.$_.capitalize(this.$t('openingBalance.balance')),
          value: 'balance'
        },
        {
          text: this.$_.capitalize(this.$t('cooperative.cooperative')),
          value: 'cooperativeName'
        },
        {
          text: '',
          value: 'actions',
          sortable: false,
          align: 'right'
        },
      ];
    }
  },
  methods: {
    loadData() {
      this.$loadingPromises([this.loadAccountRequest(), this.loadOpeningBalancesRequest()]);
    },
    loadOpeningBalances() {
      this.$loadingPromises([this.loadOpeningBalancesRequest()]);
    },
    loadOpeningBalancesRequest() {
      return OpeningBalanceService.getAllOpeningBalancesForAccount(this.accountId)
          .then(response => {
            this.openingBalances = response.data;
          })
          .catch(error => {
            this.openingBalances = [];
            this.$errorToast(error);
          });
    },
    loadAccount() {
      this.$loadingPromises([this.loadAccountRequest()]);
    },
    loadAccountRequest() {
      return AccountService.getAccountResponse(this.accountId)
          .then(response => {
            this.account = response.data;
          })
          .catch(error => {
            this.account.id = null;
            this.$errorToast(error);
          });
    },
    showEditForm(id) {
      this.editingOpeningBalanceId = id;
      this.isFormVisible = true;
    },
    showForm() {
      this.isFormVisible = true;
    },
    hideForm() {
      this.isFormVisible = false;
    },
    updateList(openingBalances) {
      this.openingBalances = openingBalances;
      this.hideForm();
    },
    currency(number) {
      return currency(number);
    },
    goBack() {
      switch (this.account.accountTypeId) {
        case 1:
          this.$router.push({name: 'cooperative', params: {id: this.account.subjectId}});
          break;
        case 2:
          this.$router.push({name: 'landlord', params: {id: this.account.subjectId}});
          break;
        case 3:
          this.$router.push({name: 'contractor', params: {id: this.account.subjectId}});
          break;
        default:
          this.$router.push({name: 'home'});
      }
    }
  },
  mounted() {
    this.loadData();
  },
  watch: {
    accountId() {
      this.loadData();
    },
    isFormVisible() {
      if (this.isFormVisible === false) {
        this.editingOpeningBalanceId = null;
      }
    }
  }
}
</script>

<style scoped>

</style>