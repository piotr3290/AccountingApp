<template>
  <div class="charge-type-list">

    <v-list class="py-0" v-if="isListVisible">
      <template v-for="(chargeType, index) in chargeTypeList">

        <v-list-item :key="chargeType.id">
          <v-list-item-title>
            {{ chargeType.number + ' - ' + chargeType.name }}
          </v-list-item-title>

          <edit-button
              class="ml-1"
              :disabled="isFormVisible"
              @click="editChargeType(chargeType.id)"/>

        </v-list-item>

        <v-divider :key="index.toString() + '-' + chargeType.id"/>

      </template>
    </v-list>

    <div v-else>
      {{ $t('chargeType.messages.noChargeType') }}
    </div>

    <div class="mt-3 text-center">
      <add-button
          :disabled="isFormVisible"
          @click="createChargeType"/>
    </div>

    <v-dialog
        v-model="isFormVisible"
        max-width="400px">

      <charge-type-form
          :id="chargeTypeId"
          @cancel="hideForm"
          @created="updateChargeTypeList($event)"
          @edited="updateChargeTypeList($event)"/>
    </v-dialog>


  </div>
</template>

<script>
import ChargeTypeForm from "@/components/charge/ChargeTypeForm";
import EditButton from "@/components/common/buttons/EditButton";
import AddButton from "@/components/common/buttons/AddButton";

export default {
  name: "ChargeTypeList",
  components: {AddButton, EditButton, ChargeTypeForm},
  props: {
    chargeTypes: {
      type: Array,
      required: true
    }
  },
  data() {
    return {
      chargeTypeList: [],
      isFormVisible: false,
      chargeTypeId: null
    }
  },
  computed: {
    isListVisible() {
      return this.chargeTypeList.length > 0;
    },
  },
  methods: {
    showForm() {
      this.isFormVisible = true;
    },
    hideForm() {
      this.isFormVisible = false;
      this.chargeTypeId = null;
    },
    editChargeType(id) {
      this.chargeTypeId = id;
      this.showForm();
    },
    createChargeType() {
      this.showForm();
    },
    updateChargeTypeList(chargeTypeList) {
      this.chargeTypeList = chargeTypeList;
      this.hideForm();
    }
  },
  mounted() {
    this.chargeTypeList = this.chargeTypes;
  },
  watch: {
    chargeTypes() {
      this.chargeTypeList = this.chargeTypes;
    },
    isFormVisible() {
      if (this.isFormVisible === false) {
        this.chargeTypeId = null;
      }
    }}
}
</script>

<style scoped>

</style>