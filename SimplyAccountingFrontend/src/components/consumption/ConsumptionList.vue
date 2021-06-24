<template>
  <div class="consumption-list">

    <v-data-table
        :headers="tableHeaders"
        :items="consumptionList"
        :no-data-text="$t('consumption.messages.noConsumption')"
        hide-default-footer>

      <template #item.chargeType="{ item }">
        {{ item.chargeTypeNumber + ' ' + item.chargeTypeName }}
      </template>

      <template #item.actions="{ item }">
        <edit-button
            :disabled="isFormVisible"
            @click="editConsumption(item.id)"/>
        <delete-button
            :disabled="isFormVisible"
            @click="deleteConsumption(item.id)"/>
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
      <consumption-form
          :premises-id="premisesId"
          :id="consumptionId"
          @created="updateList($event)"
          @updated="updateList($event)"
          @cancel="hideForm"/>
    </v-dialog>

  </div>
</template>

<script>
import ConsumptionService from "@/core/service/ConsumptionService";
import ConsumptionForm from "@/components/consumption/ConsumptionForm";
import EditButton from "@/components/common/buttons/EditButton";
import DeleteButton from "@/components/common/buttons/DeleteButton";
import AddButton from "@/components/common/buttons/AddButton";

export default {
  name: "ConsumptionList",
  components: {AddButton, DeleteButton, EditButton, ConsumptionForm},
  props: {
    consumptions: {
      type: Array,
      required: true
    },
    premisesId: {
      required: true
    }
  },
  data() {
    return {
      isFormVisible: false,
      consumptionList: [],
      consumptionId: null
    }
  },
  computed: {
    tableHeaders() {
      return [
        {
          text: this.$_.capitalize(this.$t('charge.type')),
          value: 'chargeType'
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
          text: this.$_.capitalize(this.$t('consumption.value')),
          value: 'value'
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
    deleteConsumption(id) {
      this.$loadingPromises([this.deleteConsumptionRequest(id)]);
    },
    deleteConsumptionRequest(id) {
      return ConsumptionService.deleteConsumption(id)
          .then(() => {
            this.consumptionList = this.$_.remove(this.consumptionList, consumption =>
                consumption.id !== id
            );
            this.$successToast(this.$t('consumption.messages.deleted'));
          })
          .catch(error => {
            this.$errorToast(error);
          });
    },
    showForm() {
      this.isFormVisible = true;
    },
    hideForm() {
      this.isFormVisible = false;
    },
    editConsumption(id) {
      this.consumptionId = id;
      this.showForm();
    },
    updateList(list) {
      this.consumptionList = list;
      this.hideForm();
    }
  },
  mounted() {
    this.consumptionList = this.consumptions;
  },
  watch: {
    consumptions() {
      this.consumptionList = this.consumptions;
    },
    isFormVisible() {
      if (this.isFormVisible === false) {
        this.consumptionId = null;
      }
    }
  }
}
</script>

<style scoped>

</style>