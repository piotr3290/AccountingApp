<template>
  <v-data-table
      class="rate-list elevation-1"
      :headers="tableHeaders"
      :items="rateList"
      :no-data-text="$t('rate.messages.noRate')"
      group-by="level"
      multi-sort
      show-expand
      hide-default-footer>

    <template #top>
      <v-card-title class="text-h4 justify-center">
        {{ $_.capitalize($t('rate.rates')) }}
      </v-card-title>
      <v-divider/>
    </template>

    <template #group.header="{ items, isOpen, toggle, headers }">
      <th :colspan="headers.length">
        {{ items[0].level }}
        <v-icon class="mx-2" @click="toggle">
          {{ isOpen ? 'mdi-minus' : 'mdi-plus' }}
        </v-icon>
      </th>
    </template>

    <template #item.chargeType="{ item }">
      {{ item.chargeType.number + ' ' + item.chargeType.name }}
    </template>

    <template #item.value="{ item }">
      {{ currencyFormat(item.value) }}
    </template>

    <template #item.actions="{ item }">
      <edit-button
          @click="editRate(item.id)"/>
      <delete-button
          @click="deleteRate(item.id)"/>
    </template>

    <template #expanded-item="{ headers, item }">
      <td :colspan="headers.length">
        <template v-if="item.cooperative != null">
          {{ item.cooperative.name }}
        </template>
        <template v-else-if="item.building != null">
          {{
            item.building.street + ' ' + ' ' + item.building.houseNumber + ' '
            + item.building.postalCode + ' - ' + item.building.cooperativeName
          }}
        </template>
        <template v-else-if="item.premises != null">
          {{
            item.premises.buildingStreet + ' ' + item.premises.buildingHouseNumber + ' ' + item.premises.premisesNumber
            + ' (' + item.premises.premisesTypeName + ')'
          }}
        </template>
        <template v-else>
          {{ $t('rate.messages.generalInfo') }}
        </template>
      </td>
    </template>

    <template #footer>
      <v-divider/>

      <div class="text-center">
        <add-button
            class="my-2"
            @click="createRate"/>
      </div>
    </template>

  </v-data-table>
</template>

<script>
import RateService from "@/core/service/RateService";
import EditButton from "@/components/common/buttons/EditButton";
import DeleteButton from "@/components/common/buttons/DeleteButton";
import AddButton from "@/components/common/buttons/AddButton";
import {currency} from "@/core/service/FormatterService";

export default {
  name: "RateList",
  components: {AddButton, DeleteButton, EditButton},
  data() {
    return {
      rateList: [],
    }
  },
  computed: {
    tableHeaders() {
      return [
        {
          text: this.$_.capitalize(this.$t('rate.chargeType')),
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
          text: this.$_.capitalize(this.$t('rate.value')),
          value: 'value'
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
          value: 'level'
        },
        {
          value: 'actions',
          sortable: false,
          align: 'right'
        }
      ];
    }
  },
  methods: {
    loadAllRates() {
      this.$loadingPromises([this.loadAllRatesRequest()]);
    },
    loadAllRatesRequest() {
      return RateService.getAllRates()
          .then(response => {
            this.rateList = this.$_.map(response.data, o => {
              o.level = this.$_.capitalize(this.$t(`rate.levels.${o.level}`));
              return o;
            });
          })
          .catch(error => {
            this.rateList = [];
            this.$errorToast(error);
          });
    },
    editRate(id) {
      this.$router.push({
        name: 'rateEdit',
        params: {id: id}
      });
    },
    createRate() {
      this.$router.push({name: 'rateCreate'});
    },
    deleteRate(id) {
      this.$loadingPromises([this.deleteRateRequest(id)]);
    },
    deleteRateRequest(id) {
      return RateService.deleteRate(id)
          .then(() => {
            this.rateList = this.$_.remove(this.rateList, rate => rate.id !== id);
            this.$successToast(this.$t('rate.messages.deleted'));
          })
          .catch(error => {
            this.$errorToast(error);
          })
    },
    currencyFormat(value) {
      return currency(value);
    }
  },
  mounted() {
    this.loadAllRates();
  }
}
</script>

<style scoped>

</style>