<template>
  <v-card class="premises-type-list">

    <template v-if="isErrorFree">

      <v-card-title>
        {{ $_.capitalize($t('premises.types')) }}
      </v-card-title>

      <v-divider/>

      <v-card-text class="pb-1">
        <v-data-table
            :headers="tableHeaders"
            :items="premisesTypeList"
            :no-data-text="$t('premisesType.messages.noPremisesType')"
            hide-default-footer>

          <template #item.actions="{ item }">

            <edit-button
                :disabled="isFormVisible"
                @click="editPremisesType(item.id)"/>

            <delete-button
                :disabled="isFormVisible"
                @click="deletePremisesType(item.id)"/>
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

      </v-card-text>

      <v-dialog
          v-model="isFormVisible"
          max-width="400px">
        <premises-type-form
            :id="premisesTypeId"
            @created="updatePremisesTypeList($event)"
            @edited="updatePremisesTypeList($event)"
            @cancel="hideForm"/>
      </v-dialog>
    </template>

    <template v-else>
      <v-card-title>
        {{ $t('error.defaultMessage') }}
      </v-card-title>
    </template>

  </v-card>
</template>

<script>
import PremisesTypeService from "@/core/service/PremisesTypeService";
import PremisesTypeForm from "@/components/premises/PremisesTypeForm";
import AddButton from "@/components/common/buttons/AddButton";
import DeleteButton from "@/components/common/buttons/DeleteButton";
import EditButton from "@/components/common/buttons/EditButton";

export default {
  name: "PremisesTypeList",
  components: {EditButton, DeleteButton, AddButton, PremisesTypeForm},
  data() {
    return {
      premisesTypeList: [],
      loadingError: false,
      isFormVisible: false,
      premisesTypeId: null
    }
  },
  computed: {
    isErrorFree() {
      return !this.loadingError;
    },
    tableHeaders() {
      return [
        {
          text: this.$_.capitalize(this.$t('premises.type')),
          value: 'name'
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
    editPremisesType(id) {
      this.premisesTypeId = id;
      this.showForm();
    },
    deletePremisesType(id) {
      this.$loadingPromises([this.deletePremisesTypeRequest(id)]);
    },
    deletePremisesTypeRequest(id) {
      return PremisesTypeService.deletePremisesType(id)
          .then(() => {
            this.premisesTypeList = this.$_.remove(this.premisesTypeList,
                premisesType => premisesType.id !== id);
            this.$successToast(this.$t('premisesType.messages.deleted'));
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
      this.premisesTypeId = null;
    },
    updatePremisesTypeList(premisesTypeList) {
      this.premisesTypeList = premisesTypeList;
      this.hideForm();
    },
    loadAllPremisesTypes() {
      this.$loadingPromises([this.loadAllPremisesTypesRequest()]);
    },
    loadAllPremisesTypesRequest() {
      return PremisesTypeService.getAllPremisesTypes()
          .then(response => {
            this.premisesTypeList = response.data;
            this.loadingError = false;
          })
          .catch(error => {
            this.premisesTypeList = [];
            this.loadingError = true;
            this.$errorToast(error);
          });
    }
  },
  mounted() {
    this.loadAllPremisesTypes();
  },
  watch: {
    isFormVisible() {
      if (this.isFormVisible === false) {
        this.premisesTypeId = null;
      }
    }
  }
}
</script>

<style scoped>

</style>