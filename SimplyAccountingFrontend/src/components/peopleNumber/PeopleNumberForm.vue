<template>
  <v-card class="people-number-form">

    <template v-if="isFormVisible">

      <v-card-title class="primary">
        {{ $_.capitalize($t('peopleNumber.number')) }}
      </v-card-title>

      <v-card-text class="mt-4 pb-1">

        <validation-observer ref="observer" v-slot="{handleSubmit}">
          <form id="people-number-form" @submit.prevent="handleSubmit(createEditPeopleNumber)">

            <validation-provider
                :name="$t('peopleNumber.number')"
                :rules="{required: true, min_value: 0, integer: true}"
                v-slot="validationContext">

              <number-input
                  v-model.number="peopleNumber.number"
                  tag-name="people-number"
                  :step="1"
                  :errors="validationContext.errors"
                  :label="$_.capitalize($t('peopleNumber.number'))"/>
            </validation-provider>

            <validation-provider
                :name="$t('common.from')"
                v-slot="validationContext">

              <date-input
                  v-model="peopleNumber.startDate"
                  tag-name="start-date"
                  :errors="validationContext.errors"
                  :label="$_.capitalize($t('common.from'))"
                  :clearable="true"/>
            </validation-provider>

            <validation-provider
                :name="$t('common.to')"
                v-slot="validationContext">

              <date-input
                  v-model="peopleNumber.endDate"
                  tag-name="end-date"
                  :errors="validationContext.errors"
                  :label="$_.capitalize($t('common.to'))"
                  :clearable="true"/>
            </validation-provider>

          </form>
        </validation-observer>
      </v-card-text>

      <v-card-actions class="pb-4">
        <v-spacer/>

        <default-button
            type="submit"
            form="people-number-form"
            :text="createOrEdit"/>

        <default-button
            @click="cancelForm"
            :text="$_.capitalize($t('common.cancel'))"/>
      </v-card-actions>

    </template>

    <template v-else>
      <v-card-title class="error">
        {{ $t('peopleNumber.messages.notFound') }}
      </v-card-title>

      <v-card-actions class="pb-4">
        <v-spacer/>
        <default-button
            @click="cancelForm"
            :text="$_.capitalize($t('common.cancel'))"/>
      </v-card-actions>
    </template>

  </v-card>
</template>

<script>
import PeopleNumberService from "@/core/service/PeopleNumberService";
import NumberInput from "@/components/common/form/NumberInput";
import DateInput from "@/components/common/form/DateInput";
import DefaultButton from "@/components/common/buttons/DefaultButton";

export default {
  name: "PeopleNumberForm",
  components: {DefaultButton, DateInput, NumberInput},
  props: ['id', 'premisesId'],
  data() {
    return {
      peopleNumber: {
        id: null,
        startDate: null,
        endDate: null,
        number: null,
        premisesId: null
      }
    }
  },
  methods: {
    cancelForm() {
      this.$emit('cancel');
    },
    createEditPeopleNumber() {
      this.$loadingPromises([this.createEditPeopleNumberRequest()]);
    },
    createEditPeopleNumberRequest() {
      if (this.peopleNumber.id == null) {
        return PeopleNumberService.createPeopleNumber(this.peopleNumber)
            .then(response => {
              this.$successToast(this.$t('peopleNumber.messages.created'));
              this.$emit('updatePeopleNumbers', response.data);
            })
            .catch(error => {
              this.$errorToast(error);
            });
      } else {
        return PeopleNumberService.editPeopleNumber(this.peopleNumber)
            .then(response => {
              this.$successToast(this.$t('peopleNumber.messages.edited'));
              this.$emit('updatePeopleNumbers', response.data);
            })
            .catch(error => {
              this.$errorToast(error);
            });
      }
    },
    loadPeopleNumber() {
      this.$loadingPromises([this.loadPeopleNumberRequest()]);
    },
    loadPeopleNumberRequest() {
      return PeopleNumberService.getPeopleNumber(this.id)
          .then(response => {
            this.peopleNumber = response.data;
          })
          .catch(error => {
            this.peopleNumber.id = null;
            this.$errorToast(error);
          });
    }
  },
  computed: {
    createOrEdit() {
      return this.id != null ? this.$_.capitalize(this.$t('common.edit')) : this.$_.capitalize(this.$t('common.create'));
    },
    isPeopleNumberFound() {
      return this.peopleNumber.id != null;
    },
    isFormVisible() {
      return this.isPeopleNumberFound || this.premisesId != null;
    }
  },
  mounted() {
    if (this.id != null) {
      this.loadPeopleNumber();
    } else {
      this.peopleNumber.premisesId = this.premisesId;
    }
  },
  watch: {
    id() {
      if (this.id != null || this.premisesId == null) {
        this.loadPeopleNumber();
      } else {
        this.peopleNumber.id = null;
      }
    },
    premisesId() {
      this.cancelForm();
    }
  }
}
</script>

<style scoped>

</style>