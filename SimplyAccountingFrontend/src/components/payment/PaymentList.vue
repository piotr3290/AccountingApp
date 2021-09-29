<template>
  <v-card class="payment-list">

    <v-card-title>
      {{ $_.capitalize($t('payment.bank')) }}
      <year-select
          class="mx-6"
          v-model="selectedYear"/>
      <v-spacer/>
      <default-button
          :text="cooperative.name"
          @click="goToCooperative"/>
    </v-card-title>

    <v-divider/>

    <v-card-text>
      <v-row>
        <v-col cols="6">

          <v-data-table
              mobile-breakpoint="900"
              :headers="landlordPaymentHeaders"
              :items="landlordPaymentList"
              :no-data-text="$t('payment.messages.noPayment')"
              hide-default-footer
              show-expand>

            <template #top>
              <div class="text-center mb-3 text-subtitle-1">{{ $_.capitalize($t('common.debits')) }}</div>
              <v-divider/>
            </template>

            <template #item.value="{ item }">
              {{ currency(item.value) }}
            </template>

            <template #item.landlord="{ item }">
              {{ item.landlord.lastName + ' ' + item.landlord.firstName }}
            </template>

            <template #item.actions="{ item }">
              <edit-button class="mx-1" @click="editLandlordPayment(item.id)"/>
              <delete-button class="mx-1" @click="deleteLandlordPayment(item.id)"/>
            </template>

            <template #expanded-item="{ headers, item }">
              <td :colspan="headers.length">
                {{
                  $_.capitalize($t('common.address')) + ': ' + item.premises.premisesNumber + ' ' + item.premises.buildingAddress
                }}
              </td>
            </template>

            <template #footer>

              <v-divider/>

              <div class="text-center">
                <add-button
                    class="my-2"
                    @click="createLandlordPayment"/>
              </div>

            </template>

            <default-button
                @click="createLandlordPayment"
                :text="$_.capitalize($t('common.add'))"/>

          </v-data-table>

        </v-col>

        <v-divider vertical/>

        <v-col cols="6">

          <v-data-table
              mobile-breakpoint="900"
              :headers="contractorPaymentHeaders"
              :items="contractorPaymentList"
              :no-data-text="$t('payment.messages.noPayment')"
              hide-default-footer>

            <template #top>
              <div class="text-center mb-3 text-subtitle-1">{{ $_.capitalize($t('common.credits')) }}</div>
              <v-divider/>
            </template>

            <template #item.value="{ item }">
              {{ currency(item.value) }}
            </template>

            <template #item.contractor="{ item }">
              {{ item.contractor.name }}
            </template>

            <template #item.actions="{ item }">
              <edit-button class="mx-1" @click="editContractorPayment(item.id)"/>
              <delete-button class="mx-1" @click="deleteContractorPayment(item.id)"/>
            </template>

            <template #footer>

              <v-divider/>

              <div class="text-center">
                <add-button
                    class="my-2"
                    @click="createContractorPayment"/>
              </div>

            </template>

          </v-data-table>

        </v-col>
      </v-row>
    </v-card-text>
  </v-card>
</template>

<script>
import LandlordPaymentService from "@/core/service/LandlordPaymentService";
import ContractorPaymentService from "@/core/service/ContractorPaymentService";
import YearSelect from "@/components/common/form/YearSelect";
import DefaultButton from "@/components/common/buttons/DefaultButton";
import {currency} from "@/core/service/FormatterService";
import EditButton from "@/components/common/buttons/EditButton";
import DeleteButton from "@/components/common/buttons/DeleteButton";
import AddButton from "@/components/common/buttons/AddButton";
import CooperativeService from "@/core/service/CooperativeService";

export default {
  name: "PaymentList",
  components: {AddButton, DeleteButton, EditButton, DefaultButton, YearSelect},
  props: ['cooperativeId'],
  data() {
    return {
      landlordPaymentList: [],
      contractorPaymentList: [],
      selectedYear: new Date().getFullYear(),
      cooperative: {
        id: null,
        name: null
      }
    }
  },
  computed: {
    landlordPaymentHeaders() {
      return [
        {
          text: this.$_.capitalize(this.$t('payment.value')),
          value: 'value'
        },
        {
          text: this.$_.capitalize(this.$t('common.realizationDate')),
          value: 'realizationDate'
        },
        {
          text: this.$_.capitalize(this.$t('landlord.landlord')),
          value: 'landlord'
        },
        {
          value: 'actions',
          sortable: false,
          align: 'right'
        }
      ];
    },
    contractorPaymentHeaders() {
      return [
        {
          text: this.$_.capitalize(this.$t('payment.value')),
          value: 'value'
        },
        {
          text: this.$_.capitalize(this.$t('common.realizationDate')),
          value: 'realizationDate'
        },
        {
          text: this.$_.capitalize(this.$t('contractor.contractor')),
          value: 'contractor'
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
    loadData() {
      this.$loadingPromises([
        this.loadLandlordPaymentsRequest(),
        this.loadContractorPaymentsRequest(),
        this.loadCooperativeRequest()]);
    },
    loadPayments() {
      this.$loadingPromises([this.loadLandlordPaymentsRequest(), this.loadContractorPaymentsRequest()]);
    },
    loadCooperativeRequest() {
      return CooperativeService.getCooperative(this.cooperativeId)
          .then(response => {
            this.cooperative = response.data;
          })
          .catch(error => {
            this.cooperative.id = null;
            this.$errorToast(error);
          });
    },
    loadLandlordPaymentsRequest() {
      return LandlordPaymentService.getAllLandlordPaymentsForYear(this.cooperativeId, this.selectedYear)
          .then(response => {
            this.landlordPaymentList = response.data;
          })
          .catch(error => {
            this.landlordPaymentList = [];
            this.$errorToast(error);
          });
    },
    loadContractorPaymentsRequest() {
      return ContractorPaymentService.getAllContractorPaymentsForYear(this.cooperativeId, this.selectedYear)
          .then(response => {
            this.contractorPaymentList = response.data;
          })
          .catch(error => {
            this.contractorPaymentList = [];
            this.$errorToast(error);
          });
    },
    deleteLandlordPayment(id) {
      this.$loadingPromises([this.deleteLandlordPaymentRequest(id)]);
    },
    deleteLandlordPaymentRequest(id) {
      return LandlordPaymentService.deleteLandlordPayment(id)
          .then(() => {
            this.landlordPaymentList = this.$_.remove(this.landlordPaymentList,
                landlordPayment => landlordPayment.id !== id
            );
            this.$successToast(this.$t('payment.messages.deleted'));
          })
          .catch(error => {
            this.$errorToast(error);
          });
    },
    deleteContractorPayment(id) {
      this.$loadingPromises([this.deleteContractorPaymentRequest(id)]);
    },
    deleteContractorPaymentRequest(id) {
      return ContractorPaymentService.deleteContractorPayment(id)
          .then(() => {
            this.contractorPaymentList = this.$_.remove(this.contractorPaymentList,
                contractorPayment => contractorPayment.id !== id
            );
            this.$successToast(this.$t('payment.messages.deleted'));
          })
          .catch(error => {
            this.$errorToast(error);
          });
    },
    createLandlordPayment() {
      this.$router.push({
        name: 'landlordPaymentCreate',
        params: {cooperativeId: this.cooperativeId}
      });
    },
    createContractorPayment() {
      this.$router.push({
        name: 'contractorPaymentCreate',
        params: {cooperativeId: this.cooperativeId}
      });
    },
    editLandlordPayment(id) {
      this.$router.push({
        name: 'landlordPaymentEdit',
        params: {id: id}
      });
    },
    editContractorPayment(id) {
      this.$router.push({
        name: 'contractorPaymentEdit',
        params: {id: id}
      });
    },
    currency(value) {
      return currency(value);
    },
    goToCooperative() {
      this.$router.push({
        name: 'cooperative',
        params: {id: this.cooperativeId}
      });
    }
  },
  mounted() {
    this.loadData();
  },
  watch: {
    cooperativeId() {
      this.loadData();
    },
    selectedYear() {
      this.loadPayments();
    }
  }
}
</script>

<style scoped>

.year-select {
  max-width: 100px;
}

</style>