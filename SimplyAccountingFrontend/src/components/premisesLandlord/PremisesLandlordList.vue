<template>
  <div class="premises-landlord-list">

    <v-data-table
        :headers="tableHeaders"
        :items="premisesLandlordList"
        :no-data-text="$t('premisesLandlord.messages.noPremisesLandlord')"
        hide-default-footer>

      <template #item.actions="{ item }">
        <edit-button
            :disabled="isFormVisible"
            @click="editPremisesLandlord(item.id)"/>
        <delete-button
            :disabled="isFormVisible"
            @click="deletePremisesLandlord(item.id)"/>
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

      <premises-landlord-form
          :premises-id="premisesId"
          :id="premisesLandlordId"
          @cancel="hideForm"
          @created="updateList($event)"
          @edited="updateList($event)"/>

    </v-dialog>

  </div>
</template>

<script>
import PremisesLandlordService from "@/core/service/PremisesLandlordService";
import PremisesLandlordForm from "@/components/premisesLandlord/PremisesLandlordForm";
import EditButton from "@/components/common/buttons/EditButton";
import DeleteButton from "@/components/common/buttons/DeleteButton";
import AddButton from "@/components/common/buttons/AddButton";

export default {
  name: "PremisesLandlordList",
  components: {AddButton, DeleteButton, EditButton, PremisesLandlordForm},
  props: {
    premisesLandlords: {
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
      premisesLandlordList: [],
      premisesLandlordId: null
    }
  },
  computed: {
    tableHeaders() {
      return [
        {
          text: this.$_.capitalize(this.$t('landlord.landlord')),
          value: 'landlordFullName'
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
          value: 'actions',
          sortable: false,
          align: 'right'
        }
      ];
    }
  },
  methods: {
    deletePremisesLandlord(id) {
      this.$loadingPromises([this.deletePremisesLandlordRequest(id)]);
    },
    deletePremisesLandlordRequest(id) {
      return PremisesLandlordService.deletePremisesLandlord(id)
          .then(() => {
            this.premisesLandlordList = this.$_.remove(this.premisesLandlordList,
                element => element.id !== id);
            this.$successToast(this.$t('premisesLandlord.messages.deleted'));
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
    editPremisesLandlord(id) {
      this.premisesLandlordId = id;
      this.showForm();
    },
    updateList(list) {
      this.premisesLandlordList = list;
      this.hideForm();
    }
  },
  mounted() {
    this.premisesLandlordList = this.premisesLandlords;
  },
  watch: {
    premisesLandlords() {
      this.premisesLandlordList = this.premisesLandlords;
    },
    isFormVisible() {
      if (this.isFormVisible === false) {
        this.premisesLandlordId = null;
      }
    }
  }
}
</script>

<style scoped>

</style>