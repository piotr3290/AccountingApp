<template>
  <div class="charge-list-view">
    <v-data-table
        v-if="isCooperativeFound"
        :headers="tableHeaders"
        :items="chargeElementList"
        :no-data-text="$t('charge.messages.noChargeElement')"
        group-by="chargeTypeNumber"
        multi-sort
        hide-default-footer>

      <template #top>
        <v-card-title class="text-h4">
          {{ $_.capitalize($t('charge.chargesPlan')) }}
          <add-button
              class="mx-4"
              @click="createChargeElement"/>
          <v-spacer/>
          <default-button
              :text="cooperative.name"
              @click="goToCooperative"/>
        </v-card-title>
        <v-divider/>
      </template>

      <template #group.header="{ items, isOpen, toggle, headers }">
        <th :colspan="headers.length">
          {{ items[0].chargeTypeNumber + ' ' + items[0].chargeTypeName }}
          <v-icon class="mx-2" @click="toggle">
            {{ isOpen ? 'mdi-minus' : 'mdi-plus' }}
          </v-icon>
        </th>
      </template>

      <template #item.premisesTypeName="{ item }">
        {{ item.premisesTypeName || $_.capitalize($t('common.all')) }}
      </template>

      <template #item.actions="{ item }">
        <edit-button class="mx-1" @click="editChargeElement(item.id)"/>
        <delete-button class="mx-1" @click="deleteChargeElement(item.id)"/>
      </template>

    </v-data-table>

    <v-card v-else>
      <v-card-title class="error">
        {{ $t('charge.messages.chargeElementError') }}
      </v-card-title>
    </v-card>
  </div>
</template>

<script>
import ChargeElementService from "@/core/service/ChargeElementService";
import DefaultButton from "@/components/common/buttons/DefaultButton";
import AddButton from "@/components/common/buttons/AddButton";
import EditButton from "@/components/common/buttons/EditButton";
import DeleteButton from "@/components/common/buttons/DeleteButton";
import CooperativeService from "@/core/service/CooperativeService";

export default {
  name: "ChargeListView",
  components: {DeleteButton, EditButton, AddButton, DefaultButton},
  props: ['cooperativeId'],
  data() {
    return {
      chargeElementList: [],
      cooperative: {
        id: null,
        name: null
      }
    }
  },
  computed: {
    isCooperativeFound() {
      return this.cooperative.id != null;
    },
    tableHeaders() {
      return [
        {
          text: this.$_.capitalize(this.$t('premises.type')),
          value: 'premisesTypeName'
        },
        {
          text: this.$_.capitalize(this.$t('common.from')),
          value: 'startDate'
        },
        {
          text: this.$_.capitalize(this.$t('common.to')),
          value: 'endDate'
        },
        {
          text: this.$_.capitalize(this.$t('charge.type')),
          value: 'chargeTypeNumber'
        },
        {
          value: 'actions',
          sortable: false,
          align: 'right'
        },
      ];
    }
  },
  methods: {
    deleteChargeElement(id) {
      this.$loadingPromises([this.deleteChargeElementRequest(id)]);
    },
    deleteChargeElementRequest(id) {
      return ChargeElementService.deleteChargeElement(id)
          .then(() => {
            this.chargeElementList = this.$_.remove(this.chargeElementList,
                chargeElement => chargeElement.id !== id);
            this.$successToast(this.$t('charge.messages.elementDeleted'));
          })
          .catch(error => {
            this.$errorToast(error);
          });
    },
    loadData() {
      this.$loadingPromises([this.loadCooperativeRequest(), this.loadChargeElementsRequest()]);
    },
    loadCooperative() {
      this.$loadingPromises([this.loadCooperativeRequest()]);
    },
    loadCooperativeRequest() {
      return CooperativeService.getCooperative(this.cooperativeId)
          .then(response => {
            this.cooperative = response.data;
          })
          .catch(error => {
            this.cooperative.id = null;
            this.$errorToast(error);
          });
    },
    loadChargeElements() {
      this.$loadingPromises([this.loadChargeElementsRequest()]);
    },
    loadChargeElementsRequest() {
      return ChargeElementService.getAllChargeElementsForCooperative(this.cooperativeId)
          .then(response => {
            this.chargeElementList = response.data;
          })
          .catch(error => {
            this.$errorToast(error);
          });
    },
    editChargeElement(id) {
      this.$router.push({
        name: 'chargeElementEdit',
        params: {id: id}
      });
    },
    createChargeElement() {
      this.$router.push({
        name: 'chargeElementCreate',
        params: {cooperativeId: this.cooperativeId}
      });
    },
    goToCooperative() {
      this.$router.push({name: 'cooperative', params: {id: this.cooperative.id}});
    }
  },
  mounted() {
    this.loadData();
  },
  watch: {
    cooperativeId() {
      this.loadData();
    }
  }
}
</script>

<style scoped>

</style>