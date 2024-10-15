package com.sandun.app.model;

//public class PagingSource<R , D> extends PagingSourceCompact<R, D> {
//    public PagingSource(R service, int itemSize, NestedScrollView scrollView, RecyclerView recyclerView, Activity activity) {
//        super(service, itemSize, scrollView, recyclerView, activity);
//    }
//
//    @Override
//    protected void init() {
//        ItemAdapter adapter = new ItemAdapter(getList());
//        setAdapter(adapter);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(activity) {
//            @Override
//            public boolean canScrollVertically() {
//                return false;
//            }
//        };
//
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        RecyclerView recyclerView = getRecyclerView();
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(adapter);
//
//        loadData();
//
//    }
//
//    @Override
//    public void loadData() {
//        if (isLoading == LoadingStates.DONE) {
//            changeLoadingState(LoadingStates.LOADING);
//            ItemService service = (ItemService) this.service;
//            service.getItemsByPage("test.php", currentPage, itemSize).enqueue(new Callback<JsonObject>() {
//                @Override
//                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                    currentPage++;
//                    System.out.println(response.body());
//                    JsonArray array = response.body().getAsJsonArray("response");
//                    List<ItemDTO> list = (List<ItemDTO>) getList();
//                    RecyclerView.Adapter adapter = getAdapter();
//                    for (JsonElement element : array) {
//                        list.add(new ItemDTO());
//                        adapter.notifyItemChanged(list.size() - 1);
//                    }
//                    System.out.println("das");
//                    changeLoadingState(LoadingStates.DONE);
//                }
//
//                @Override
//                public void onFailure(Call<JsonObject> call, Throwable throwable) {
//                    throwable.printStackTrace();
//                    System.out.println("error");
//                    changeLoadingState(LoadingStates.ERROR);
//                }
//            });
//        }
//    }
//
//    @Override
//    protected void changeLoadingState(LoadingStates state) {
//        isLoading = state;
//        stateListener.listener();
//    }
//
//}
