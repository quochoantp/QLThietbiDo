<template>
  <div>
    <div class="grid">
      <table class="table-Info">
        <thead>
          <th class="tdNumber"><input type="checkbox" /></th>
          <th class="tdNumber">#</th>
          <th class="tdButton">Thao tác</th>

          <th class="tdInfo" v-for="(field, key) in fields" :key="key">
            <input
              class="inputSearch"
              v-if="searchable"
              type="text"
              :placeholder="field.text"
              v-model="searchData[key].text"
              @keyup.enter="putSearchData"
            />
            <div v-else>{{ field.text }}</div>
          </th>
        </thead>
        <tbody>
          <tr v-for="(item, index) in displayedPosts" v-bind:key="index">
            <td class="tdNumber"><input type="checkbox" /></td>
            <td class="tdNumber">{{ index + 1 }}</td>
            <td class="tdButton">
              <button class="btn-icon" @click="onClickUpdate(item)">
                <i class="fas fa-edit"></i>
              </button>
            </td>

            <td class="tdInfo" v-for="(field, key) in fields" :key="key">
              {{ getDataParse(item[field.name], field.name) }}
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <div class="marginCenter" style="width: 100%">
      <ul class="pagination">
        <li class="">
          <button
            type="button"
            class="page-link"
            @click="page = 1"
            v-if="page != 1 && page != 2"
          >
            First
          </button>
        </li>
        <li class="">
          <button
            type="button"
            class="page-link"
            @click="page--"
            v-if="page != 1"
          >
            Previous
          </button>
        </li>
        <li>
          <button
            type="button"
            class="page-link"
            v-for="pageNumber in pages.slice(page - 1, page + 2)"
            @click="page = pageNumber"
            v-bind:key="pageNumber"
          >
            {{ pageNumber }}
          </button>
        </li>
        <li>
          <button
            type="button"
            class="page-link"
            @click="page++"
            v-if="page < pages.length - 1"
          >
            Next
          </button>
        </li>
        <li>
          <button
            type="button"
            class="page-link"
            @click="page = pages.length"
            v-if="page < pages.length"
          >
            Last
          </button>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
export default {
  data() {
    return {
      page: 1,

      pages: [],
      //    updatedData : [
      //        {name: "ins_name", text: ""},
      //        {name: "ins_code", text: ""},
      //        {name: "parameter_type_name", text: ""},
      //        {name: "manufact", text: ""},
      //        {name: "ins_model", text: ""},
      //        {name: "status", text: ""},
      //        {name: "is_control_enable",text:""},
      //        {name: "is_observable" , text:""}
      //    ]
    };
  },
  props: {
    searchData: [],
    fields: Array,
    data: Array,
    perPage: Number,
    searchable: {
      type: Boolean,
      default: false,
    },
    state: String,
  },

  methods: {
    //    ...mapMutations(['setUpdatedData']),
    //    ...mapActions(['updateProfile']),
    onClickUpdate(item) {
      this.$emit("onclick-update", item);
      //this.updateProfile(item)
    },
    setPages() {
      this.pages = [];
      let numberOfPages = Math.ceil(this.data.length / this.perPage);
      console.log(numberOfPages);
      for (let index = 1; index <= numberOfPages; index++) {
        this.pages.push(index);
      }
    },
    paginate(data) {
      if (data.length != 0) {
        let page = this.page;
        let perPage = this.perPage;
        let from = page * perPage - perPage;
        let to = page * perPage;
        return data.slice(from, to);
      } else {
        return data;
      }
    },
    getDataParse(data, item) {
      if (item == "parameter_type_name_unit") {
        var str = "";
        data.forEach((element) => {
          str += element;
          str += "; ";
        });
        str = str.substring(0, str.length - 2);
        return str;
      } else {
        return data;
      }
    },
    putSearchData() {
      this.$emit("putSearchData", this.searchData);
      console.log(this.searchData);
    },
  },

  computed: {
    ...mapGetters(["updatedData"]),
    displayedPosts() {
      return this.paginate(this.data);
    },
  },
  created() {
    this.setPages();
  },
  watch: {
    data() {
      this.setPages();
    },
    perPage() {
      this.setPages();
    },
  },
};
</script>

<style scoped>
.grid {
  overflow: auto;
  height: 80%;
  max-width: 100%;
}
table {
  border-collapse: separate;
  width: 100%;
  border-spacing: 0;
  table-layout: fixed;
}
th {
  position: sticky;
  top: 0;
  z-index: 5;
  background-color: #fff;
  border-top: 1px solid #bbb;
}
th,
td {
  height: 48px;
  max-height: 200px;
  padding: 0 16px;
  text-align: left;
  border-bottom: 1px solid #bbb;
  border-right: 1px solid #bbb;
}
.tdNumber {
  text-align: center;
  width: 50px;
}
.tdButton {
  text-align: center;
  width: 100px;
}
.tdInfo {
  width: 220px;
  line-break: auto;
}
td:nth-child(1) {
  border-left: 1px solid #bbb;
}
th:nth-child(1) {
  border-left: 1px solid #bbb;
}

.grid::-webkit-scrollbar-track {
  margin-top: 48px;
  width: 6px;
  background-color: #bbb;
}
.inputSearch {
  border-radius: 15px;
  text-align: center;
  width: 100%;
  height: 60%;
}
.grid::-webkit-scrollbar {
  width: 5px !important;
  height: 5px !important;
}
.grid::-webkit-scrollbar-thumb {
  border-radius: 8px;
  background-color: #808080;
}
.btn-icon {
  border: none;
  outline: none;
  background: #fff;
}
.page-link {
  display: inline-block;
  font-size: 18px;
  color: blue;
  border-radius: 5px;
}
.marginCenter {
  display: flex;
  justify-content: flex-end;
  width: 100%;
}
</style>
