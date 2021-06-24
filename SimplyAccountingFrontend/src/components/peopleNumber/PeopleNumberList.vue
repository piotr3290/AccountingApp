<template>
  <div class="people-number-list">

    <v-data-table
        :headers="tableHeaders"
        :items="peopleNumberList"
        :no-data-text="$t('peopleNumber.messages.noPeopleNumber')"
        hide-default-footer>

      <template #item.actions="{ item }">
        <edit-button
            :disabled="isFormVisible"
            @click="editPeopleNumber(item.id)"/>
        <delete-button
            :disabled="isFormVisible"
            @click="deletePeopleNumber(item.id)"/>
      </template>

      <template #footer>

        <v-divider/>

        <div class="text-center">
          <add-button
              :disabled="isFormVisible"
              class="my-2"
              @click="createPeopleNumber"/>
        </div>
      </template>

    </v-data-table>

    <v-dialog
        v-model="isFormVisible"
        max-width="400px">

      <people-number-form
          :premises-id="premisesId"
          :id="peopleNumberId"
          @updatePeopleNumbers="updatePeopleNumber($event)"
          @cancel="hidePeopleNumberForm"/>
    </v-dialog>
  </div>
</template>

<script>
import PeopleNumberService from "@/core/service/PeopleNumberService";
import PeopleNumberForm from "@/components/peopleNumber/PeopleNumberForm";
import EditButton from "@/components/common/buttons/EditButton";
import AddButton from "@/components/common/buttons/AddButton";
import DeleteButton from "@/components/common/buttons/DeleteButton";

export default {
  name: "PeopleNumberList",
  components: {DeleteButton, AddButton, EditButton, PeopleNumberForm},
  props: {
    peopleNumbers: {
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
      peopleNumberList: [],
      peopleNumberId: null
    }
  },
  computed: {
    tableHeaders() {
      return [
        {
          text: this.$_.capitalize(this.$t('common.from')),
          value: 'startDate'
        },
        {
          text: this.$_.capitalize(this.$t('common.to')),
          value: 'endDate'
        },
        {
          text: this.$_.capitalize(this.$t('peopleNumber.number')),
          value: 'number'
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
    deletePeopleNumber(peopleNumberId) {
      this.$loadingPromises([this.deletePeopleNumberRequest(peopleNumberId)]);
    },
    deletePeopleNumberRequest(peopleNumberId) {
      return PeopleNumberService.deletePeopleNumber(peopleNumberId)
          .then(() => {
            this.$_.remove(this.peopleNumberList, peopleNumber =>
                peopleNumber.id !== peopleNumberId
            );
            this.$successToast(this.$t('peopleNumber.messages.deleted'));
          })
          .catch(error => {
            this.$errorToast(error);
          });
    },
    showPeopleNumberForm() {
      this.isFormVisible = true;
    },
    hidePeopleNumberForm() {
      this.isFormVisible = false;
      this.peopleNumberId = null;
    },
    editPeopleNumber(id) {
      this.peopleNumberId = id;
      this.showPeopleNumberForm();
    },
    createPeopleNumber() {
      this.showPeopleNumberForm();
    },
    updatePeopleNumber(peopleNumberList) {
      this.peopleNumberList = peopleNumberList;
      this.hidePeopleNumberForm();
    }
  },
  mounted() {
    this.peopleNumberList = this.peopleNumbers;
  },
  watch: {
    peopleNumbers() {
      this.peopleNumberList = this.peopleNumbers;
    },
    isFormVisible() {
      if (this.isFormVisible === false) {
        this.peopleNumberId = null;
      }
    }
  }
}
</script>

<style scoped>

</style>