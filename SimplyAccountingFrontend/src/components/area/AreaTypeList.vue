<template>
  <v-card class="area-type-list">

    <template v-if="isErrorFree">

      <v-card-title>
        {{ $_.capitalize($t('area.types')) }}
      </v-card-title>

      <v-divider/>

      <v-card-text class="pb-1">
        <v-data-table
            :headers="tableHeaders"
            :items="areaTypeList"
            :no-data-text="$t('areaType.messages.noAreaType')"
            hide-default-footer>

          <template #item.actions="{ item }">

            <edit-button
                :disabled="isFormVisible"
                @click="editAreaType(item.id)"/>

            <delete-button
                :disabled="isFormVisible"
                @click="deleteAreaType(item.id)"/>
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
        <area-type-form
            :id="areaTypeId"
            @created="updateAreaTypeList($event)"
            @edited="updateAreaTypeList($event)"
            @cancel="hideForm"/>
      </v-dialog>

    </template>

    <template v-else>
      <v-card-title class="primary">
        {{ $t('error.defaultMessage') }}
      </v-card-title>
    </template>
  </v-card>
</template>

<script>
import AreaTypeService from "@/core/service/AreaTypeService";
import AreaTypeForm from "@/components/area/AreaTypeForm";
import EditButton from "@/components/common/buttons/EditButton";
import DeleteButton from "@/components/common/buttons/DeleteButton";
import AddButton from "@/components/common/buttons/AddButton";

export default {
  name: "AreaTypeList",
  components: {AddButton, DeleteButton, EditButton, AreaTypeForm},
  data() {
    return {
      areaTypeList: [],
      loadingError: false,
      isFormVisible: false,
      areaTypeId: null
    }
  },
  computed: {
    isErrorFree() {
      return !this.loadingError;
    },
    tableHeaders() {
      return [
        {
          text: this.$_.capitalize(this.$t('area.type')),
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
    editAreaType(id) {
      this.areaTypeId = id;
      this.showForm();
    },
    deleteAreaType(id) {
      this.$loadingPromises([this.deleteAreaTypeRequest(id)]);
    },
    deleteAreaTypeRequest(id) {
      return AreaTypeService.deleteAreaType(id)
          .then(() => {
            this.areaTypeList = this.$_.remove(this.areaTypeList, areaType => areaType.id !== id);
            this.$successToast(this.$t('areaType.messages.deleted'));
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
      this.areaTypeId = null;
    },
    updateAreaTypeList(areaTypeList) {
      this.areaTypeList = areaTypeList;
      this.hideForm();
    },
    loadAllAreaTypes() {
      this.$loadingPromises([this.loadAllAreaTypesRequest()]);
    },
    loadAllAreaTypesRequest() {
      return AreaTypeService.getAllAreaTypes()
          .then(response => {
            this.areaTypeList = response.data;
            this.loadingError = false;
          })
          .catch(error => {
            this.areaTypeList = [];
            this.loadingError = true;
            this.$errorToast(error);
          });
    }
  },
  mounted() {
    this.loadAllAreaTypes();
  },
  watch: {
    isFormVisible() {
      if (this.isFormVisible === false) {
        this.areaTypeId = null;
      }
    }
  }
}
</script>

<style scoped>

</style>