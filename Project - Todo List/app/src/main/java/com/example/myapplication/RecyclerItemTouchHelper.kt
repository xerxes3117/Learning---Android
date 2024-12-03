import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.motion.widget.OnSwipe
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adapter.TaskRecyclerViewAdapter

class RecyclerItemTouchHelper(
    private val context: Context,
    private val adapter: TaskRecyclerViewAdapter,
    private val onTaskDeleted: (Int) -> Unit,
    private val onSwipeRight: (Int) -> Unit
) : ItemTouchHelper.Callback() {

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        if (direction == ItemTouchHelper.LEFT) {
            AlertDialog.Builder(context).also {
                it.setTitle("Delete Task")
                it.setMessage("Are you sure?")
                it.setPositiveButton("Yes") { _, _ ->
                    onTaskDeleted(adapter.getTaskId(position))
                }
                it.setNegativeButton("No") { _, _ ->
                    adapter.notifyItemChanged(position) //@Doubt: Why call notifyItemChanged here?
                }
            }.create().show()
        } else if (direction == ItemTouchHelper.RIGHT) {
            onSwipeRight(adapter.getTaskId(position))
        }
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val itemView = viewHolder.itemView
        val icon: Drawable
        val background: ColorDrawable

        if (dX > 0) { // Swiping to the right
            icon = ContextCompat.getDrawable(context, android.R.drawable.ic_menu_edit)!!
            background = ColorDrawable(context.getColor(android.R.color.holo_green_light))
            val iconMargin = (itemView.height - icon.intrinsicHeight) / 2
            val iconTop = itemView.top + (itemView.height - icon.intrinsicHeight) / 2
            val iconBottom = iconTop + icon.intrinsicHeight

            val iconLeft = itemView.left + iconMargin
            val iconRight = iconLeft + icon.intrinsicWidth
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)

            background.setBounds(itemView.left, itemView.top, itemView.left + dX.toInt(), itemView.bottom)
        } else if (dX < 0) { // Swiping to the left
            icon = ContextCompat.getDrawable(context, android.R.drawable.ic_menu_delete)!!
            background = ColorDrawable(context.getColor(android.R.color.holo_red_light))
            val iconMargin = (itemView.height - icon.intrinsicHeight) / 2
            val iconTop = itemView.top + (itemView.height - icon.intrinsicHeight) / 2
            val iconBottom = iconTop + icon.intrinsicHeight

            val iconRight = itemView.right - iconMargin
            val iconLeft = iconRight - icon.intrinsicWidth
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)

            background.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
        } else { // View is unSwiped
            background = ColorDrawable()
            icon = ColorDrawable()
            background.setBounds(0, 0, 0, 0)
            icon.setBounds(0, 0, 0, 0)
        }

        background.draw(c)
        icon.draw(c)

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}