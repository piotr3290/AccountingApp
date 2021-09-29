<template>
  <v-card class="advance-list">
    <v-data-table
        v-if="isPremisesFound"
        :headers="tableHeaders"
        :items="advanceList"
        :no-data-text="$t('advance.messages.noAdvance')"
        hide-default-footer>

      <template #top>
        <v-card-title>
          {{ $_.capitalize($t('advance.advances')) }}
          <year-select
              class="mx-6"
              v-model="selectedYear"/>
          <v-spacer/>
          <default-button
              :text="$_.capitalize($t('premises.premises'))"
              @click="goToPremises"/>
        </v-card-title>

        <v-card-subtitle class="py-0">
          {{ premises.buildingStreet + ' ' + premises.buildingHouseNumber + ' ' + premises.premisesNumber }}
        </v-card-subtitle>

        <v-card-subtitle class="pt-0">
          {{ premises.cooperativeName }}
        </v-card-subtitle>
        <v-divider/>
      </template>

      <template #item.month="{ item }">
        {{ item.month + '-' + item.year }}
      </template>

      <template #item.amount="{ item }">
        {{ currencyFormat(item.amount) }}
      </template>

      <template #item.chargeType="{ item }">
        {{ item.chargeType.number + ' ' + item.chargeType.name }}
      </template>

      <template #item.actions="{ item }">
        <edit-button
            @click="editAdvance(item.id)"/>
        <delete-button
            @click="deleteAdvance(item.id)"/>
      </template>

      <template #footer>
        <v-divider/>

        <div class="text-center">
          <add-button
              class="my-2"
              @click="createAdvance"/>
        </div>
      </template>

    </v-data-table>

    <v-card-title
        v-else
        class="error">
      {{ $t('premises.messages.notFound') }}
    </v-card-title>
  </v-card>
</template>

<script>
import YearSelect from "@/components/common/form/YearSelect";
import {currency} from "@/core/service/FormatterService";
import AdvanceService from "@/core/service/AdvanceService";
import AddButton from "@/components/common/buttons/AddButton";
import EditButton from "@/components/common/buttons/EditButton";
import DeleteButton from "@/components/common/buttons/DeleteButton";
import PremisesService from "@/core/service/PremisesService";
import DefaultButton from "@/components/common/buttons/DefaultButton";

export default {
  name: "AdvanceList",
  components: {DefaultButton, DeleteButton, EditButton, AddButton, YearSelect},
  props: ['premisesId'],
  data() {
    return {
      advanceList: [],
      premises: {
        id: null,
        premisesNumber: null,
        premisesTypeName: null,
        cooperativeName: null,
        buildingStreet: null,
        buildingHouseNumber: null,
        buildingId: null
      },
      selectedYear: new Date().getFullYear()
    }
  },
  computed: {
    isPremisesFound() {
      return this.premises.id != null;
    },
    tableHeaders() {
      return [
        {
          text: this.$_.capitalize(this.$t('common.month')),
          value: 'month'
        },
        {
          text: this.$_.capitalize(this.$t('advance.amount')),
          value: 'amount'
        },
        {
          text: this.$_.capitalize(this.$t('advance.chargeType')),
          value: 'chargeType',
          sort: (a, b) => {
            if (a.number > b.number)
              return 1;
            else if (a.number < b.number)
              return -1;
            else
              return 0;
          }
        },
        {
          text: this.$_.capitalize(this.$t('landlord.landlord')),
          value: 'landlordFullName'
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
    currencyFormat(value) {
      return currency(value);
    },
    editAdvance(id) {
      this.$router.push({
        name: 'advanceEdit',
        params: {id: id}
      });
    },
    createAdvance() {
      this.$router.push({
        name: 'advanceCreate',
        params: {premisesId: this.premisesId}
      });
    },
    loadData() {
      this.$loadingPromises([this.loadPremisesRequest(), this.loadAllAdvancesRequest()]);
    },
    loadAllAdvances() {
      this.$loadingPromises([this.loadAllAdvancesRequest()]);
    },
    loadAllAdvancesRequest() {
      return AdvanceService.getAllAdvancesForPremises(this.premisesId, this.selectedYear)
          .then(response => {
            this.advanceList = response.data;
          })
          .catch(error => {
            this.advanceList = [];
            this.$errorToast(error);
          });
    },
    loadPremises() {
      this.$loadingPromises([this.loadPremisesRequest()]);
    },
    loadPremisesRequest() {
      return PremisesService.getPremisesInfo(this.premisesId)
          .then(response => {
            this.premises = response.data;
          })
          .catch(error => {
            this.premises.id = null;
            this.$errorToast(error);
          });
    },
    deleteAdvance(id) {
      this.$loadingPromises([this.deleteAdvanceRequest(id)]);
    },
    deleteAdvanceRequest(id) {
      return AdvanceService.deleteAdvance(id)
          .then(async () => {
            this.$successToast(this.$t('advance.messages.deleted'));
            await this.loadAllAdvancesRequest();
          })
          .catch(error => {
            this.$errorToast(error);
          });
    },
    goToPremises() {
      this.$router.push({
        name: 'premises',
        params: {id: this.premisesId}
      })
    }
  },
  mounted() {
    this.loadData();
  },
  watch: {
    premisesId() {
      this.loadData();
    },
    selectedYear() {
      this.loadAllAdvances();
    }
  }
}
</script>

<style scoped>

.year-select {
  max-width: 100px;
}

</style>