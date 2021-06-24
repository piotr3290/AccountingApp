<template>
  <div class="area-list">
    <v-data-table
        :headers="tableHeaders"
        :items="areasList"
        :no-data-text="$t('area.messages.noArea')"
        hide-default-footer>

      <template #item.actions="{ item }">
        <edit-button
            :disabled="isFormVisible"
            @click="editArea(item.id)"/>
        <delete-button
            :disabled="isFormVisible"
            @click="deleteArea(item.id)"/>
      </template>

      <template #footer>

        <v-divider/>

        <div class="text-center">
          <add-button
              :disabled="isFormVisible"
              class="my-2"
              @click="showAreaForm"/>
        </div>
      </template>


    </v-data-table>

    <v-dialog
        v-model="isFormVisible"
        max-width="400px">
      <area-form
          :premises-id="premisesId"
          :id="areaId"
          @updateAreas="updateAreas($event)"
          @cancel="hideAreaForm"/>
    </v-dialog>
  </div>
</template>

<script>
import AreaService from "@/core/service/AreaService";
import AreaForm from "@/components/area/AreaForm";
import EditButton from "@/components/common/buttons/EditButton";
import DeleteButton from "@/components/common/buttons/DeleteButton";
import AddButton from "@/components/common/buttons/AddButton";

export default {
  name: "AreaList",
  components: {AddButton, DeleteButton, EditButton, AreaForm},
  props: {
    areas: {
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
      areasList: [],
      areaId: null
    }
  },
  computed: {
    tableHeaders() {
      return [
        {
          text: this.$_.capitalize(this.$t('area.type')),
          value: 'areaTypeName'
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
          text: this.$_.capitalize(this.$t('area.surface')),
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
    deleteArea(areaId) {
      this.$loadingPromises([this.deleteAreaRequest(areaId)]);
    },
    deleteAreaRequest(areaId) {
      return AreaService.deleteArea(areaId)
          .then(() => {
            this.areasList = this.$_.remove(this.areasList, area =>
                area.id !== areaId
            );
            this.$successToast(this.$t('area.messages.deleted'));
          })
          .catch(error => {
            this.$errorToast(error);
          });
    },
    showAreaForm() {
      this.isFormVisible = true;
    },
    hideAreaForm() {
      this.isFormVisible = false;
      this.areaId = null;
    },
    editArea(id) {
      this.areaId = id;
      this.showAreaForm();
    },
    updateAreas(areas) {
      this.areasList = areas;
      this.hideAreaForm();
    }
  },
  mounted() {
    this.areasList = this.areas;
  },
  watch: {
    areas() {
      this.areasList = this.areas;
    },
    isFormVisible() {
      if (this.isFormVisible === false) {
        this.areaId = null;
      }
    }
  }
}
</script>

<style scoped>

</style>